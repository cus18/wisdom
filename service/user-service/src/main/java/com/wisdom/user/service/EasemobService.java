//package com.wisdom.system.service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.wisdom.common.dto.UserInfoDTO;
//import com.wisdom.common.util.ConstantUtil;
//import com.wisdom.common.util.FileUtils;
//import com.wisdom.common.util.HttpRequestUtil;
//import com.wisdom.common.util.OSSObjectTool;
//import com.wisdom.user.dto.*;
//import com.wisdom.user.mapper.EasemobGroupMapper;
//import com.wisdom.user.mapper.EasemobMapper;
//import com.wisdom.user.mapper.ElderUserMapper;
//import com.wisdom.user.mapper.PractitionerUserMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Service;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * Created by zbm84 on 2017/5/8.
// */
//
//@Service
//public class EasemobService {
//
//
//    private static String org_name = "1156170425115453";
//    private static String app_name = "laoyoupractitioner";
//    private static String clientID = "YXA6sEf7ADnpEeeL3W144MlC";
//    private static String clientSecret = "YXA60ZXFcB-UZ0TdWx3etIcqOo4ySAw";
//    private static final String SRC = "https://a1.easemob.com/" + org_name + "/" + app_name + "/";
//    private static final String adminEasemobID = "健康宝";
//
//
//    @Autowired
//    private EasemobMapper easemobMapper;
//
//    @Autowired
//    private EasemobGroupMapper easemobGroupMapper;
//
//    @Autowired
//    protected MongoTemplate mongoTemplate;
//
//    @Autowired
//    private ElderUserMapper elderUserMapper;
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private PractitionerUserMapper practitionerUserMapper;
//
//    /**
//     * 更新环信接口使用的 Token
//     *
//     * @throws Exception
//     */
//    public void updateEasemobToken() throws Exception {
//        String content = "{\"grant_type\":\"client_credentials\",\"client_id\":\"" +
//                clientID + "\",\"client_secret\":\"" + clientSecret + "\"}";
//        String result = HttpRequestUtil.httpsRequest(SRC + "token", "POST", content);
//        EasemobDTO easemobDTO = JSON.parseObject(result, EasemobDTO.class);
//        easemobMapper.updateEasemobToken(easemobDTO);
//    }
//
//    public String getEasemobToken() {
//        EasemobDTO easemob = easemobMapper.getEasemobToken();
//        return easemob.getAccessToken();
//    }
//
//
//    /**
//     * 注册环信用户
//     *
//     * @param userID   用户的ID  必须
//     * @param password 用户的密码 必须
//     * @param nickname 用户昵称  可选参数
//     * @return
//     * @throws Exception
//     */
//    public boolean signEasemobUser(String userID, String password, String... nickname) throws Exception {
//        String content = "{\"username\":\"" + userID + "\",\"password\":\"" + password + "\"}";
//        if (nickname.length > 0) {
//            content = "{\"username\":\"" + userID + "\",\"password\":\"" + password +
//                    "\",\"nickname\":\"" + nickname[0] + "\"}";
//        }
//        System.out.println(content);
//        System.out.println(SRC);
//        String result = HttpRequestUtil.httpsRequest(SRC + "users", "POST", content);
//        if (result != null) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * 调用环信接口创建群组
//     *
//     * @param groupName 群组名称
//     * @param desc      群组描述
//     * @param pub       数组是否对外开放
//     * @param maxusers  群组最大允许人数
//     * @param approval  加入群组是否需要群主批准
//     * @param owner     群组拥有者
//     * @param members   群组成员
//     * @return 创建是否成功
//     * @throws Exception
//     */
//    public boolean createEasemobGroup(String groupName, String desc, boolean pub,
//                                      Integer maxusers, boolean approval, String owner,
//                                      String elderEasemobID, String[] members) throws Exception {
//        String content = "{";
//        content += "\"groupname\":\"" + groupName + "\",";
//        content += "\"desc\":\"" + desc + "\",";
//        content += "\"public\":" + pub + ",";
//        content += "\"maxusers\":" + maxusers + ",";
//        content += "\"approval\":" + approval + ",";
//        content += "\"owner\":\"" + owner + "\",";
//        String doctorIDArray = "";
//        for (String easemobID : members) {
//            doctorIDArray += easemobID;
//        }
//        content += " \"members\":[\"" + elderEasemobID + "\",\"" + doctorIDArray + "\"]";
//        content += "}";
//        System.out.println(content);
//        String result = HttpRequestUtil.httpsRequest(SRC + "chatgroups",
//                "POST", content, this.getEasemobToken());
//        Map<String, Object> map = (Map<String, Object>) JSON.parse(result.split(">>>>")[1]);
//        EasemobGroupDTO easemobGroupDTO = new EasemobGroupDTO();
//        easemobGroupDTO.setEasemobGroupID(((Map<String, Object>) JSON.parse(map.get("data").toString())).
//                get("groupid").toString());
//        easemobGroupDTO.setGroupName(groupName);
//        easemobGroupDTO.setOwner(owner);
//        easemobGroupDTO.setElderEasemobID(elderEasemobID);
//        easemobGroupDTO.setDoctorIDArray(elderEasemobID+";");
//        Integer res = easemobGroupMapper.insertEasemobGroup(easemobGroupDTO);
//        if (res > 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * 调用环信接口创建群组
//     *
//     * @param groupName 群组名称
//     * @param desc      群组描述
//     * @param owner     群组拥有者
//     * @param members     群组成员
//     * @return 创建是否成功
//     * @throws Exception
//     */
//    public String createEasemobGroup(String groupName, String desc, String owner,String members) {
//        String content = "{";
//        content += "\"groupname\":\"" + groupName + "\",";
//        content += "\"desc\":\"" + desc + "\",";
//        content += "\"public\":true,";
//        content += "\"maxusers\":999,";
//        content += "\"approval\":false,";
//        content += "\"owner\":\"" + owner + "\",";
//        content += " \"members\":[\"" + members + "\"]";
//        content += "}";
//        System.out.println(content);
//        String result = HttpRequestUtil.httpsRequest(SRC + "chatgroups",
//                "POST", content, this.getEasemobToken());
//        System.out.println(result);
//        return result;
//    }
//
//    public boolean joinEasemobGroup(String groupID, String easemobID) {
//        String result = HttpRequestUtil.httpsRequest(SRC + "chatgroups/" +
//                groupID + "/users/" + easemobID, "POST", "", this.getEasemobToken());
//        System.out.println(result);
//        if (result.equals("403>>>>")) {
//            return false;
//        }
//        return  true;
//    }
//
//
//    public boolean addUserByEasemobGroup(String groupID, String easemobID,
//                                         String type, String oldEasemobID,
//                                         String elderID, String doctorName) throws Exception {
//        String result = HttpRequestUtil.httpsRequest(SRC + "chatgroups/" + groupID + "/users/" + easemobID, "POST", "", "YWMtsKn4UDnpEee5KaM3VTu7UAAAAAAAAAAAAAAAAAAAAAGwR_sAOekR54vdbXjgyUL5AgMAAAFcD1mhMgBPGgDDwzX8kVVpl9lC8i3e_GZGxl4wl6qCrUcP3IZ6remH9g");
//        System.out.println(result);
//        if (result.equals("403>>>>")) {
//            return false;
//        }
//        EasemobGroupDTO easemobGroupDTO = easemobGroupMapper.getEasemobGroupByGroupID(groupID);
//        if (type.equals("2")) {
//            easemobGroupDTO.setNurse(easemobID);
//            Query query = new Query(Criteria.where("elderId").is(elderID));
//            BasicInfoDTO b = mongoTemplate.findAndRemove(query, BasicInfoDTO.class, "healthArchive_basicInfo");
//            b.setNurseName(doctorName);
//            mongoTemplate.insert(b, "healthArchive_basicInfo");
//        } else if (type.equals("1")) {
//            easemobGroupDTO.setOwner(easemobID);
//            Query query = new Query(Criteria.where("elderId").is(elderID));
//            BasicInfoDTO b = mongoTemplate.findAndRemove(query, BasicInfoDTO.class, "healthArchive_basicInfo");
//            b.setDoctorName(doctorName);
//            mongoTemplate.insert(b, "healthArchive_basicInfo");
//        }
//        if (!type.equals("1")) {
//            String newDoctorArray = "";
//            if (easemobGroupDTO.getDoctorIDArray() == null) {
//
//            } else {
//                String[] doctorIDArray = easemobGroupDTO.getDoctorIDArray().split(";");
//                for (String d : doctorIDArray) {
//                    if (d.equals(oldEasemobID)) {
//                        //        String delResult = HttpRequestUtil.httpsRequest(SRC + "chatgroups/", "POST", content, EasemobService.getEasemobToken());
//                        String delResult = HttpRequestUtil.httpsRequest(SRC + "chatgroups/" + groupID + "/users/" + d, "DELETE", "", "YWMtsKn4UDnpEee5KaM3VTu7UAAAAAAAAAAAAAAAAAAAAAGwR_sAOekR54vdbXjgyUL5AgMAAAFcD1mhMgBPGgDDwzX8kVVpl9lC8i3e_GZGxl4wl6qCrUcP3IZ6remH9g");
//                        System.out.println(delResult);
//                        continue;
//                    } else {
//                        newDoctorArray += d + ";";
//                    }
//                }
//            }
//            newDoctorArray += easemobID + ";";
//            easemobGroupDTO.setDoctorIDArray(newDoctorArray);
//        }
//        Integer res = easemobGroupMapper.updateEasemobGroup(easemobGroupDTO);
//        if (res > 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public boolean delUserByEasemobGroup(String groupID, String easemobID, String type) throws Exception {
//        String result = HttpRequestUtil.httpsRequest(SRC + "chatgroups" + groupID + "/users/" + easemobID, "DELETE", "", "YWMtsKn4UDnpEee5KaM3VTu7UAAAAAAAAAAAAAAAAAAAAAGwR_sAOekR54vdbXjgyUL5AgMAAAFcD1mhMgBPGgDDwzX8kVVpl9lC8i3e_GZGxl4wl6qCrUcP3IZ6remH9g");
//        Map<String, Object> map = (Map<String, Object>) JSON.parse(result);
//        EasemobGroupDTO easemobGroupDTO = easemobGroupMapper.getEasemobGroupByGroupID(groupID);
//        if (type.equals("1")) {
//            easemobGroupDTO.setNurse("");
//        } else if (type.equals("2")) {
//            easemobGroupDTO.setOwner("");
//        }
//        Integer res = easemobGroupMapper.updateEasemobGroup(easemobGroupDTO);
//        if (res > 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * 获取环信历史聊天记录
//     *
//     * @throws Exception
//     */
//    public void getEasemobChatRecord() throws Exception {
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
//        Date dt = sdf.parse(sdf.format(new Date()));
//        Calendar rightNow = Calendar.getInstance();
//        rightNow.setTime(dt);
//        rightNow.add(Calendar.HOUR_OF_DAY, -2);
//        Date dt1 = rightNow.getTime();
//        String result = HttpRequestUtil.sendGet("http://a1.easemob.com/1156170425115453/laoyoupractitioner/chatmessages/" + "2017052323", "YWMttb9jgj9ZEeelyfHlOrnOgwAAAAAAAAAAAAAAAAAAAAGwR_sAOekR54vdbXjgyUL5AgMAAAFcMvxe-gBPGgCDXUDLKTajp5XbSce8-jsxyY-LOhec6vW9Wror2DVP6A");
//        String fileName = UUID.randomUUID().toString() + ".7z";
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        if (jsonObject.get("error") == null) {
//            String data = jsonObject.get("data").toString();
//            jsonObject = JSONObject.parseArray(data).getJSONObject(0);
//            String url = jsonObject.get("url").toString();
//            FileUtils.downLoadFromUrl(url, fileName, "../ChatRcord/");
//            fileName = FileUtils.unGzipFile("../ChatRcord/" + fileName);
//            txt2String("../ChatRcord/" + fileName);
//            System.out.println(result);
//        }
//    }
//
//    /**
//     * 读取环信聊天记录文件
//     *
//     * @param filePath
//     * @return
//     */
//    public String txt2String(String filePath) {
//        File file = new File(filePath);
//        StringBuilder result = new StringBuilder();
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));  //构造一个BufferedReader类来读取文件
//            String s = null;
//            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
//                String chatMessage = System.lineSeparator() + s;
//                JSONObject jsonObject = JSONObject.parseObject(chatMessage);
//                EasemobChatMessageDTO easemobChatMessageDTO = new EasemobChatMessageDTO();
//                easemobChatMessageDTO.setId(UUID.randomUUID().toString());
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Long time = new Long(jsonObject.get("timestamp").toString());
//                easemobChatMessageDTO.setSendTime(format.parse(format.format(time)));
//                easemobChatMessageDTO.setToEasemobID(jsonObject.get("to").toString());
//                easemobChatMessageDTO.setUserEasemobID(jsonObject.get("from").toString());
//                easemobChatMessageDTO.setChatType(jsonObject.get("chat_type").toString());
//                JSONObject message = JSONObject.parseObject(jsonObject.get("payload").toString());
//                String bodies = message.get("bodies").toString();
//                message = JSONObject.parseArray(bodies).getJSONObject(0);
//                String type = message.get("type").toString();
//                if (type.equals("txt")) {
//                    easemobChatMessageDTO.setChatType(type);
//                    easemobChatMessageDTO.setMessage(message.get("msg").toString());
//                } else if (type.equals("audio")) {
//                    easemobChatMessageDTO.setChatType(type);
//                    String fileName = UUID.randomUUID().toString() + ".mp3";
//                    FileUtils.downLoadFromUrl(message.get("url").toString(), fileName, "../ChatRcord/" + fileName);
//                    File file1 = new File("\"../ChatRcord/\"+fileName");
//                    OSSObjectTool.upLoadFileToOSS(file1, fileName);
//                    easemobChatMessageDTO.setMessage("http://yhllaoyou.oss-cn-beijing.aliyuncs.com/ChatFile/" + fileName);
//                    easemobChatMessageDTO.setAudioLength(message.get("length").toString());
//                } else if (type.equals("img")) {
//                    easemobChatMessageDTO.setChatType(type);
//                    String fileName = UUID.randomUUID().toString() + ".png";
//                    FileUtils.downLoadFromUrl(message.get("url").toString(), fileName, "../ChatRcord/" + fileName);
//                    File file1 = new File("\"../ChatRcord/\"+fileName");
//                    OSSObjectTool.upLoadFileToOSS(file1, fileName);
//                    easemobChatMessageDTO.setMessage("http://yhllaoyou.oss-cn-beijing.aliyuncs.com/ChatFile/" + fileName);
//                    easemobChatMessageDTO.setAudioLength(message.get("length").toString());
//                }
//                this.mongoTemplate.insert(easemobChatMessageDTO, "EasemobChatRcord");
//            }
//            br.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result.toString();
//    }
//
//    public static void sendEasemobMessage(String easemobGroupID, String message) {
//        String content = "{\"target_type\" : \"chatgroups\","; // users 给用户发消息。chatgroups: 给群发消息，chatrooms: 给聊天室发消息
//        content += "\"target\" : [\"" + easemobGroupID + "\"],"; // 注意这里需要用数组，数组长度建议不大于20，即使只有一个用户，也要用数组 ['u1']，给用户发送时数组元素是用户名，给群组发送时数组元素是groupid
//        content += "\"msg\" : {";
//        content += "\"type\" : \"txt\",";
//        content += "\"msg\" : \"" + message + "\""; //消息内容，参考[[start:100serverintegration:30chatlog|聊天记录]]里的bodies内容
//        content += "},";
//        content += "\"from\" : \"" + adminEasemobID + "\""; //表示消息发送者。无此字段Server会默认设置为'from':'admin'，有from字段但值为空串(" ")时请求失败
//        content += "}";
//        System.out.println(content);
//        String result = HttpRequestUtil.httpsRequest(SRC + "messages", "POST", content, "YWMtsKn4UDnpEee5KaM3VTu7UAAAAAAAAAAAAAAAAAAAAAGwR_sAOekR54vdbXjgyUL5AgMAAAFcD1mhMgBPGgDDwzX8kVVpl9lC8i3e_GZGxl4wl6qCrUcP3IZ6remH9g");
//        System.out.println(result);
//    }
//
//    public void sendEasemobMessage(String easemobIDs, String message,String... chatType) {
//        String type=chatType.length==0?"chatgroups":chatType[0];
//        String content = "{\"target_type\" : \""+type+"\","; // users 给用户发消息。chatgroups: 给群发消息，chatrooms: 给聊天室发消息
//        content += "\"target\" : [" + easemobIDs + "],"; // 注意这里需要用数组，数组长度建议不大于20，即使只有一个用户，也要用数组 ['u1']，给用户发送时数组元素是用户名，给群组发送时数组元素是groupid
//        content += "\"msg\" : {";
//        content += "\"type\" : \"txt\",";
//        content += "\"msg\" : \"" + message + "\""; //消息内容，参考[[start:100serverintegration:30chatlog|聊天记录]]里的bodies内容
//        content += "},";
//        content += "\"from\" : \"" + adminEasemobID + "\""; //表示消息发送者。无此字段Server会默认设置为'from':'admin'，有from字段但值为空串(" ")时请求失败
//        content += "}";
//        System.out.println(content);
//        String result = HttpRequestUtil.httpsRequest(SRC + "messages",
//                "POST", content, this.getEasemobToken());
//        System.out.println(result);
//    }
//
//    /**
//     * 修改环信昵称
//     *
//     * @param easemobUserID 环信用户的ID
//     * @param nickname      用户昵称
//     * @return
//     * @throws Exception
//     */
//    public boolean updateEasemobUserNickName(String easemobUserID, String nickname) throws Exception {
//        String content = "{\"nickname\" : \"" + nickname + "\"}";
//        System.out.println(content);
//        System.out.println(SRC);
//        String result = HttpRequestUtil.httpsRequest(SRC + "users/"+easemobUserID,
//                "POST", content,this.getEasemobToken());
//        if (result != null) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public EasemobGroupDTO getEasemobGroup(String elderId) {
//        EasemobGroupDTO easemobGroupDTO = easemobGroupMapper.getEasemobGroupIDByElderID(elderId);
//        return easemobGroupDTO;
//    }
//
//
//    public GroupMemberDTO getEasemobGroupByGroupID(String groupID) {
//        EasemobGroupDTO easemobGroupDTO = easemobGroupMapper.getEasemobGroupByGroupID(groupID);
//        GroupMemberDTO groupMemberDTO = new GroupMemberDTO();
//        ElderUserDTO elderUserDTO = elderUserMapper.getSysElderUserByEasemobID(easemobGroupDTO.getElderEasemobID());
//        UserInfoDTO userInfo = new UserInfoDTO();
//        userInfo.setId(elderUserDTO.getSysUserID());
//        List<UserInfoDTO> userInfoDTOList = userMapper.getUserByInfo(userInfo);
//        groupMemberDTO.setElderID(userInfoDTOList.get(0).getId());
//        groupMemberDTO.setElderName(userInfoDTOList.get(0).getName());
//        groupMemberDTO.setElderPhoto(userInfoDTOList.get(0).getPhoto());
//        groupMemberDTO.setElderMemberCardID(elderUserDTO.getMemberCardID());
//        PractitionerUserDTO practitionerUserDTO = practitionerUserMapper.getSysPractitionerByEasemobID(easemobGroupDTO.getOwner());
//        groupMemberDTO.setOwnerID(practitionerUserDTO.getId());
//        groupMemberDTO.setOwnerName(userInfoDTOList.get(0).getName());
//        groupMemberDTO.setOwnerPhoto(userInfoDTOList.get(0).getPhoto());
//        groupMemberDTO.setOwnerType(Integer.parseInt(practitionerUserDTO.getType()));
//
//        if (practitionerUserDTO == null) {
//            groupMemberDTO.setNurseName("");
//        } else {
//            groupMemberDTO.setNurseName(userInfoDTOList.get(0).getName());
//        }
//        if (easemobGroupDTO.getDoctorIDArray() != null) {
//            String[] doctorID = easemobGroupDTO.getDoctorIDArray().split(";");
//            List<GroupDoctorDTO> list = new ArrayList<GroupDoctorDTO>();
//            for (String doctor : doctorID) {
//                try {
//                    GroupDoctorDTO groupDoctorDTO = new GroupDoctorDTO();
//                    PractitionerUserDTO doc = practitionerUserMapper.getSysPractitionerByEasemobID(doctor);
//                    groupDoctorDTO.setDoctorID(doc.getId());
//                    groupDoctorDTO.setDoctorName(userInfoDTOList.get(0).getName());
//                    groupDoctorDTO.setDoctorPhoto(userInfoDTOList.get(0).getPhoto());
//                    groupDoctorDTO.setDoctorType(Integer.parseInt(doc.getType()));
//                    list.add(groupDoctorDTO);
//                }catch (Exception e){
//                    continue;
//                }
//            }
//            groupMemberDTO.setGroupDoctorDTOList(list);
//        }
//        return groupMemberDTO;
//    }
//
//    public String getEasemobMessageUrl(String... params) {
//        String returnUrl = "";
//        switch (params[0]) {
//            case "chatType1":
//                returnUrl = "practitionerURL@@@" + ConstantUtil.EASEMOB_MESSAGE_URL
//                        + "/practitioner/#/groupChat/chatType1," + params[1]
//                        + "@@@" + ConstantUtil.EASEMOB_MESSAGE_URL + "/practitioner/#/healthServicePackage/"
//                        + params[1] + ",existHealthServicePackage";
//                break;
//            case "chatType2":
//                returnUrl = "practitionerURL@@@" + ConstantUtil.EASEMOB_MESSAGE_URL
//                        + "/practitioner/#/groupChat/chatType2," + params[1]
//                        + "@@@" + ConstantUtil.EASEMOB_MESSAGE_URL + "/practitioner/#/physicalExamination/" + params[1];
//                break;
//            case "chatType3":
//                break;
//            case "chatType4":
//                returnUrl = "practitionerURL@@@" + ConstantUtil.EASEMOB_MESSAGE_URL
//                        + "/practitioner/#/groupChat/chatType4," + params[1]
//                        + "@@@" + ConstantUtil.EASEMOB_MESSAGE_URL + "/practitioner/#/bloodPressureRecord/"
//                        + "true," + params[2] + "," + params[3] + "," + params[4] + "," + params[5] + ",true";
//                break;
//            case "chatType5":
//                returnUrl = "practitionerURL@@@" + ConstantUtil.EASEMOB_MESSAGE_URL
//                        + "/practitioner/#/groupChat/chatType5," + params[1]
//                        + "@@@" + ConstantUtil.EASEMOB_MESSAGE_URL + "/practitioner/#/bloodSugarRecord/"
//                        + params[2] + ",true," + params[3] + "," + params[4] + ",true";
//                break;
//            case "chatType6":
//                returnUrl = "practitionerURL@@@" + ConstantUtil.EASEMOB_MESSAGE_URL
//                        + "/practitioner/#/groupChat/chatType6," + params[1]
//                        + "@@@" + ConstantUtil.EASEMOB_MESSAGE_URL + "/practitioner/#/testReportResult/"
//                        + params[2] + "," + params[3];
//                break;
//            case "chatType7":
//                returnUrl = "practitionerURL@@@" + ConstantUtil.EASEMOB_MESSAGE_URL
//                        + "/practitioner/#/groupChat/chatType7," + params[1]
//                        + "@@@" + ConstantUtil.EASEMOB_MESSAGE_URL + "/practitioner/#/diagnoseReportResult/" + params[2];
//                break;
//            case "chatType8":
//                break;
//        }
//
//        return returnUrl;
//    }
//
//}
