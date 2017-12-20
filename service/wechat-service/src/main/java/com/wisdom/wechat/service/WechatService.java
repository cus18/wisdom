package com.wisdom.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.util.HttpRequestUtil;
import com.wisdom.common.util.StringUtils;
import com.wisdom.wechat.entity.*;
import com.wisdom.wechat.mapper.WeChatAttentionMapper;
import com.wisdom.wechat.util.EmojiFilter;
import com.wisdom.wechat.util.MessageUtil;
import com.wisdom.wechat.util.ReceiveXmlProcess;
import com.wisdom.wechat.util.WechatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

@Service
@Transactional(readOnly = false)
@Component
public class WechatService {

	@Autowired
	RedisService redisService;

	@Autowired
	public static  RedisService staticRedisService;

	@Autowired
	private WeChatAttentionMapper weChatAttentionMapper;

	//华录老友
//	private static final String appid="wx07acc4feaf7d07d3";
//
//	private static final String secret="a13b8339bd15fb3a498a002f9ece36b3";

	//测试微信号
	public static final String appid="wx952c2a0a6b0d63c0";

	public static final String secret="d4624c36b6795d1d99dcf0547af5443d";

	@PostConstruct
	public void init() {
		WechatService.staticRedisService = this.redisService;
	}

	//获取 Token
	public static String getWechatToken() {
		try {
			String token=staticRedisService.get("WeChatToken");
			//调用微信获取菜单接口测试 Token 是否有效
			String result=HttpRequestUtil.get("https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+ token);
			Map<String,Object> map= JSONObject.parseObject(result,Map.class);
			String errcode=map.get("errcode")+"";
			if(map.get("errcode")==null||!map.get("errcode").equals("40001")){
				return token;
			}else {
				result = HttpRequestUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WechatService.appid + "&secret=" + WechatService.secret);
				map = JSONObject.parseObject(result, Map.class);
				token = map.get("access_token")+"";
				staticRedisService.set("WeChatToken", token);
				return token;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	//定时器更新 Token
	@Scheduled(initialDelay=1000, fixedRate=2*60*60*1000)
	public void updateWechatToken() {
		try {
			String result= HttpRequestUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ WechatService.appid+"&secret="+WechatService.secret);
			Map<String,String> map= JSONObject.parseObject(result,Map.class);
			redisService.set("WeChatToken",map.get("access_token"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 处理微信发来的请求
	 *
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request,HttpServletResponse response) throws IOException {
		System.out.println("processPatientRequest===================================");
		String respMessage = null;

		/** 解析xml数据 */
		ReceiveXmlEntity xmlEntity = ReceiveXmlProcess.Instance.getMsgEntity(getXmlDataFromWechat(request));
		String msgType = xmlEntity.getMsgType();

		// xml请求解析
		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
			// 事件类型
			String eventType = xmlEntity.getEvent();
			if(eventType.equals(MessageUtil.SCAN)){
				//已关注公众号的情况下扫描
				this.updateAttentionInfo(xmlEntity);
				respMessage = processScanEvent(xmlEntity,"oldUser",request,response);
			}else if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
				//扫描关注公众号或者搜索关注公众号都在其中
				respMessage = processSubscribeEvent(xmlEntity, request,response);
			}
			// 取消订阅
			else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
				processUnSubscribeEvent(xmlEntity, request);
			}
			// 自定义菜单点击事件
			else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
				respMessage = processClickMenuEvent(xmlEntity,request,response);
			}
			// 结束咨询对话
			else if(eventType.equals(MessageUtil.KF_CLOSE)){
				respMessage = processCloseEvent(xmlEntity,request,response);
			}
			//获取用户位置
			else if (eventType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)){
				processGetLocationEvent(xmlEntity,request);
			}
		}
		return respMessage;
	}

	public class processConsultMessageThread extends Thread {
		private ReceiveXmlEntity xmlEntity;

		public processConsultMessageThread(ReceiveXmlEntity xmlEntity) {
			this.xmlEntity = xmlEntity;
		}

		public void run() {
			try {
				System.out.println(xmlEntity.getContent());
				if(xmlEntity.getMsgType().equals("text")){
					this.sendPost(ConfigConstant.ANGEL_WEB_URL + "angel/consult/wechat/conversation",
							"openId=" + xmlEntity.getFromUserName() +
							"&messageType=" + xmlEntity.getMsgType() +
							"&messageContent=" + URLEncoder.encode(xmlEntity.getContent(), "UTF-8"));
				}else{
					this.sendPost(ConfigConstant.ANGEL_WEB_URL + "angel/consult/wechat/conversation",
							"openId=" + xmlEntity.getFromUserName() +
							"&messageType=" + xmlEntity.getMsgType() +
							"&mediaId=" + xmlEntity.getMediaId());
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 向指定 URL 发送POST方法的请求
		 *
		 * @param url
		 *            发送请求的 URL
		 * @param param
		 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
		 * @return 所代表远程资源的响应结果
		 */
		public String sendPost(String url, String param) {
			PrintWriter out = null;
			BufferedReader in = null;
			String result = "";
			try {
				URL realUrl = new URL(url);
				// 打开和URL之间的连接
				URLConnection conn = realUrl.openConnection();
				// 设置通用的请求属性
				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("user-agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				// 发送POST请求必须设置如下两行
				conn.setDoOutput(true);
				conn.setDoInput(true);
				// 获取URLConnection对象对应的输出流
				out = new PrintWriter(conn.getOutputStream());
				// 发送请求参数
				out.print(param);
				// flush输出流的缓冲
				out.flush();
				// 定义BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			} catch (Exception e) {
				System.out.println("发送 POST 请求出现异常！"+e);
				e.printStackTrace();
			}
			//使用finally块来关闭输出流、输入流
			finally{
				try{
					if(out!=null){
						out.close();
					}
					if(in!=null){
						in.close();
					}
				}
				catch(IOException ex){
					ex.printStackTrace();
				}
			}
			return result;
		}
	}

	private String getXmlDataFromWechat(HttpServletRequest request)
	{
		/** 读取接收到的xml消息 */
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		try {
			is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private String processScanEvent(ReceiveXmlEntity xmlEntity,
									String userType,
									HttpServletRequest request,
									HttpServletResponse response)
	{
		String EventKey = xmlEntity.getEventKey();
		System.out.println(EventKey + "EventKey=========================================");
		Article article = new Article();
		List<Article> articleList = new ArrayList<Article>();
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(xmlEntity.getFromUserName());
		newsMessage.setFromUserName(xmlEntity.getToUserName());
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);
//		if(EventKey.indexOf("baoxian_000001")>-1&&xmlEntity.getEvent().equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
//			TextMessage textMessage = new TextMessage();
//			textMessage.setToUserName(xmlEntity.getFromUserName());
//			textMessage.setFromUserName(xmlEntity.getToUserName());
//			textMessage.setCreateTime(new Date().getTime());
//			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
//			textMessage.setFuncFlag(0);
//			textMessage.setContent("尊敬的诺安康VIP客户，您好！欢迎加入宝大夫，让您从此育儿不用愁！\n\n【咨询大夫】直接咨询北京三甲医院儿科专家，一分钟内极速回复！\n\n【妈妈活动】添加宝大夫客服微信：bdfdxb，加入宝大夫家长群，与众多宝爸宝妈一起交流分享，参与更多好玩的活动！\n\n如需人工协助，请您拨打：400-623-7120。\n");
//			return MessageUtil.textMessageToXml(textMessage);
//
//		}
//		else if(EventKey.indexOf("xuanjianghuodong_zhengyuqiao_saoma")>-1)
//		{
//			article.setDescription("您好，欢迎关注！" +
//					"\n\n点击进入宝大夫-郑玉巧育儿经，一起交流学习育儿健康管理知识！");
//			article.setUrl("http://baodf.com/titan/wechatInfo/fieldwork/wechat/author?" +
//					"url=http://baodf.com/titan/wechatInfo/getUserWechatMenId?url=4");
//			articleList.add(article);
//		}
//        String toOpenId = xmlEntity.getFromUserName();//扫码者openid
//        Map<String, Object> param1 = new HashMap<String, Object>();
//        param1.put("openid", toOpenId);
//		if(articleList.size() == 0){
//			return "";
//		}
		// 设置图文消息个数
		newsMessage.setArticleCount(articleList.size());
		// 设置图文消息包含的图文集合
		newsMessage.setArticles(articleList);
		// 将图文消息对象转换成xml字符串
		String respMessage = MessageUtil.newsMessageToXml(newsMessage);
		return respMessage;
	}

    /**
     * 发送HttpPost请求
     *
     * @param strURL
     *            服务地址
     * @param params
     *            json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/>
     *            type (请求方式：POST,GET)
     * @return 成功:返回json字符串<br/>
     */
    public String post(String strURL, String params,String type) {

        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod(type); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            int length = (int) connection.getContentLength();// 获取长度
            InputStream is = connection.getInputStream();
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                String result = new String(data, "UTF-8"); // utf-8编码
                System.out.println(result);
                return result;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null; // 自定义错误信息
    }

	private String processSubscribeEvent(ReceiveXmlEntity xmlEntity,HttpServletRequest request,HttpServletResponse response)
	{
		//获取微信可以发送消息的token
		String token = WechatService.getWechatToken();
		String EventKey = xmlEntity.getEventKey();
		String marketer = "";
		if(StringUtils.isNotNull(EventKey)){
			marketer = EventKey.replace("qrscene_", "");
		}
		this.insertAttentionInfo(xmlEntity, token, marketer);
		return sendSubScribeMessage(xmlEntity, request,response, marketer, token);
	}

	private void insertAttentionInfo(ReceiveXmlEntity xmlEntity,String token,String marketer)
	{
		HashMap<String,Object> map = new HashMap<String, Object>();
		String openId = xmlEntity.getFromUserName();
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		WechatBean wechatBean = WechatUtil.getWechatName(token, openId);
		WeChatAttention weChatAttention=new WeChatAttention();
		weChatAttention.setStatus("0");
		weChatAttention.setOpenid(openId);
		weChatAttention.setMarketer(marketer);
		weChatAttention.setNickname(EmojiFilter.coverEmoji(wechatBean.getNickname()));
		weChatAttentionMapper.insertWeChatAttention(weChatAttention);
    }

    private void updateAttentionInfo(ReceiveXmlEntity xmlEntity)
	{
		String EventKey = xmlEntity.getEventKey();
		String openId = xmlEntity.getFromUserName();
		String marketer = EventKey.replace("qrscene_", "");
		WechatBean wechatBean = WechatUtil.getWechatName(WechatService.getWechatToken(), openId);
		WeChatAttention weChatAttention=new WeChatAttention();
		weChatAttention.setStatus("0");
		weChatAttention.setOpenid(openId);
		weChatAttention.setMarketer(marketer);
		weChatAttention.setNickname(EmojiFilter.coverEmoji(wechatBean.getNickname()));
		weChatAttentionMapper.insertWeChatAttention(weChatAttention);
	}

	private String sendSubScribeMessage(ReceiveXmlEntity xmlEntity,HttpServletRequest request,HttpServletResponse response,String marketer,String token)
	{
		HttpSession session = request.getSession();
		session.setAttribute("openId", xmlEntity.getFromUserName());
		String EventKey = xmlEntity.getEventKey();
		if(EventKey.indexOf("xuanjianghuodong_zhengyuqiao_saoma")<=-1&&EventKey.indexOf("baoxian_000001")<=-1)
		{
			List<Article> articleList = new ArrayList<Article>();
			Article article = new Article();
			article.setTitle("宝大夫送你一份见面礼");
			article.setDescription("");
			article.setPicUrl("http://xiaoerke-wxapp-pic.oss-cn-hangzhou.aliyuncs.com/menu/%E5%AE%9D%E6%8A%A4%E4%BC%9Ebanner2%20-%20%E5%89%AF%E6%9C%AC%20%E6%8B%B7%E8%B4%9D.png");
			article.setUrl("http://s165.baodf.com/wisdom/umbrella#/umbrellaLead/130000000/a");
			articleList.add(article);

			article = new Article();
			article.setTitle("咨询大夫\n一分钟极速回复，7×24全年无休");
			article.setDescription("三甲医院医生7X24全年无休   一分钟极速回复");
			article.setPicUrl("http://xiaoerke-wxapp-pic.oss-cn-hangzhou.aliyuncs.com/menu/%E5%92%A8%E8%AF%A2%E5%A4%A7%E5%A4%AB.png");
			article.setUrl("https://mp.weixin.qq.com/s?__biz=MzI2MDAxOTY3OQ==&mid=504236660&idx=1&sn=10d923526047a5276dd9452b7ed1e302&scene=1&srcid=0612OCo7d5ASBoGRr2TDgjfR&key=f5c31ae61525f82ed83c573369e70b8f9b853c238066190fb5eb7b8640946e0a090bbdb47e79b6d2e57b615c44bd82c5&ascene=0&uin=MzM2NjEyMzM1&devicetype=iMac+MacBookPro11%2C4+OSX+OSX+10.11.4+build(15E65)&version=11020201&pass_ticket=dG5W6eOP3JU1%2Fo3JXw19SFBAh1DgpSlQrAXTyirZuj970HMU7TYojM4D%2B2LdJI9n");
			articleList.add(article);

			article = new Article();
			article.setTitle("名医面诊\n线上轻松预约，线下准时专家面诊");
			article.setDescription("三甲医院儿科专家，线上准时预约，线下准时就诊");
			article.setPicUrl("http://xiaoerke-wxapp-pic.oss-cn-hangzhou.aliyuncs.com/menu/%E5%90%8D%E5%8C%BB%E9%9D%A2%E8%AF%8A.png");
			article.setUrl("http://s251.baodf.com/keeper/wechatInfo/fieldwork/wechat/author?url=http://s251.baodf.com/keeper/wechatInfo/getUserWechatMenId?url=2");
			articleList.add(article);

			article = new Article();
			article.setTitle("妈妈社群\n育儿交流找组织，客服微信：bdfdxb");
			article.setDescription("添加宝大夫客服微信：bdfdxb，加入宝大夫家长群，与众多宝妈一起交流分享，参与更多好玩儿的活动");
			article.setPicUrl("http://xiaoerke-wxapp-pic.oss-cn-hangzhou.aliyuncs.com/menu/%E5%A6%88%E5%A6%88%E6%B4%BB%E5%8A%A8.png");
			article.setUrl("https://mp.weixin.qq.com/s?__biz=MzI2MDAxOTY3OQ==&mid=504236661&idx=3&sn=4c1fd3ee4eb99e6aca415f60dceb6834&scene=1&srcid=0616uPcrUKz7FVGgrmOcZqqq&from=singlemessage&isappinstalled=0&key=18e81ac7415f67c44d3973b3eb8e53f264f47c1109eceefa8d6be994349fa7f152bb8cfdfab15b36bd16a4400cd1bd87&ascene=0&uin=MzM2NjEyMzM1&devicetype=iMac+MacBookPro11%2C4+OSX+OSX+10.11.4+build(15E65)&version=11020201&pass_ticket=ZgGIH5%2B8%2FkhHiHeeRG9v6qbPZmK5qPlBL02k0Qo%2FHCK7eLMOZexAypBy0dzPjzaZ");
			articleList.add(article);

			WechatUtil.senImgMsgToWechat(token,xmlEntity.getFromUserName(),articleList);

		}

		return processScanEvent(xmlEntity,"newUser",request,response);
	}

	private void processUnSubscribeEvent(ReceiveXmlEntity xmlEntity,HttpServletRequest request)
	{
		// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
		HttpSession session = request.getSession();
		session.setAttribute("openId", xmlEntity.getFromUserName());
		HashMap<String,Object> map = new HashMap<String, Object>();
		String openId = xmlEntity.getFromUserName();
		WechatBean wechatBean = WechatUtil.getWechatName(WechatService.getWechatToken(), openId);
		WeChatAttention weChatAttention=new WeChatAttention();
		weChatAttention.setStatus("1");
		weChatAttention.setOpenid(openId);
		weChatAttention.setMarketer("");
		weChatAttention.setNickname(EmojiFilter.coverEmoji(wechatBean.getNickname()));
		weChatAttentionMapper.insertWeChatAttention(weChatAttention);
	}

	private String processClickMenuEvent(ReceiveXmlEntity xmlEntity,
										 HttpServletRequest request,
										 HttpServletResponse response)
	{
		// TODO 自定义菜单
		String respMessage = "";
		if("39".equals(xmlEntity.getEventKey())){
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(xmlEntity.getFromUserName());
			textMessage.setFromUserName(xmlEntity.getToUserName());
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			String st = "您好，我是宝大夫专职医护，愿为您提供医学服务，协助预约儿科专家，请您拨打：400-623-7120。";
			textMessage.setContent(st);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		}
		else if("38".equals(xmlEntity.getEventKey()))
		{
			HttpSession session = request.getSession();
			session .setAttribute("openId",xmlEntity.getFromUserName());

			Map parameter = null;
			String token = (String) parameter.get("token");
			List<Article> articleList = new ArrayList<Article>();
			Article article = new Article();
			article.setTitle("三甲医院儿科专家    咨询秒回不等待");
			article.setDescription("小儿内科:       24小时全天 \n\n小儿皮肤科/保健科:   8:00 ~ 23:00\n\n妇产科:   12:00-14:00，19:00-22:00\n" +
					"\n小儿其他专科:   19:00 ~ 21:00\n\n" +
					"(外科、眼科、耳鼻喉科、口腔科、预防接种科、中医科、心理科)\n\n好消息！可在线咨询北京儿童医院皮肤科专家啦！");
			article.setPicUrl("http://xiaoerke-wxapp-pic.oss-cn-hangzhou.aliyuncs.com/menu/%E6%8E%A8%E9%80%81%E6%B6%88%E6%81%AF2.png");
			article.setUrl("https://mp.weixin.qq.com/s?__biz=MzI2MDAxOTY3OQ==&mid=504236660&idx=1&sn=10d923526047a5276dd9452b7ed1e302&scene=1&srcid=0612OCo7d5ASBoGRr2TDgjfR&key=f5c31ae61525f82ed83c573369e70b8f9b853c238066190fb5eb7b8640946e0a090bbdb47e79b6d2e57b615c44bd82c5&ascene=0&uin=MzM2NjEyMzM1&devicetype=iMac+MacBookPro11%2C4+OSX+OSX+10.11.4+build(15E65)&version=11020201&pass_ticket=dG5W6eOP3JU1%2Fo3JXw19SFBAh1DgpSlQrAXTyirZuj970HMU7TYojM4D%2B2LdJI9n");
			articleList.add(article);
			WechatUtil.senImgMsgToWechat(token,xmlEntity.getFromUserName(),articleList);
		}else if("36".equals(xmlEntity.getEventKey()))
		{
			List<Article> articleList = new ArrayList<Article>();
			// 创建图文消息
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(xmlEntity.getFromUserName());
			newsMessage.setFromUserName(xmlEntity.getToUserName());
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);
			Article article = new Article();
			article.setTitle("咨询大夫，请关注最新公众账号!");
			article.setDescription("公告：本公众号将于2015年8月9日起停止服务。需要咨询大夫，" +
					"请妈妈们识别二维码关注最新的公众号（微信号：BaodfWX）。专业的大夫在线等您来咨询哦！");
			article.setPicUrl("http://mmbiz.qpic.cn/mmbiz/dGa0GvlZMicotZvyd4ZkHKjYe3ERsP5OD0spQbFz1CPTd" +
					"qqWjrP1s5pr4BpDxvM97NgYNm4PiazfHv37t6kbP9dw/640?wx_fmt=jpeg&wxfrom=5");
			article.setUrl("http://mp.weixin.qq.com/s?__biz=MzI0MjAwNjY0Nw==&mid=208340985&idx=1&sn=2beb0d78fc9097f10d073e182f1cb6c1&scene=0#rd");
			articleList.add(article);
			// 设置图文消息个数
			newsMessage.setArticleCount(articleList.size());
			// 设置图文消息包含的图文集合
			newsMessage.setArticles(articleList);
			// 将图文消息对象转换成xml字符串
			respMessage = MessageUtil.newsMessageToXml(newsMessage);
		}

		return respMessage;
	}

	private String processCloseEvent(ReceiveXmlEntity xmlEntity,HttpServletRequest request,HttpServletResponse response)
	{
		String respMessage = null;
		String openId=xmlEntity.getFromUserName();
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("openid", openId);
		 params.put("uuid", UUID.randomUUID().toString().replaceAll("-", ""));
		 params.put("starNum1", 0);
		 params.put("starNum2", 0);
		 params.put("starNum3", 0);
		 params.put("doctorId", xmlEntity.getKfAccount());
		 params.put("content", "");
		 params.put("dissatisfied", null);
		 params.put("redPacket", null);
		return respMessage;
	}

	private void processGetLocationEvent(ReceiveXmlEntity xmlEntity,HttpServletRequest request)
	{
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("id",UUID.randomUUID().toString().replaceAll("-", ""));
		map.put("latitude",xmlEntity.getLatitude());
		map.put("precision",xmlEntity.getPrecision());
		map.put("createTime",xmlEntity.getCreateTime());
		map.put("openid",xmlEntity.getFromUserName());
		map.put("longitude", xmlEntity.getLongitude());
	}

}
