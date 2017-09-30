package com.wisdom.user.interceptor;

import com.wisdom.common.dto.ResponseDTO;
import com.wisdom.user.constant.ConfigConstant;
import com.wisdom.user.constant.StatusConstant;
import com.wisdom.user.service.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

@Aspect
@Component
public class LoginRequiredInterceptor {

    @Autowired
    RedisService redisService;

    /**
     * 定义拦截规则：拦截com.xjj.web.controller包下面的所有类中，有@RequestMapping注解的方法。
     */
    @Pointcut("execution(* com.wisdom.user.controller..*(..)) && " +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut(){}

    /**
     * 拦截器具体实现
     * @param pjp
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
     */
    @Around("controllerMethodPointcut()")
    public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod(); //获取被拦截的方法

        Set<Object> allParams = new LinkedHashSet<Object>(); //保存所有请求参数，用于输出到日志中

        Object result = null;

        Object[] args = pjp.getArgs();
        for(Object arg : args){
            //logger.debug("arg: {}", arg);
            if (arg instanceof Map<?, ?>) {
                //提取方法中的MAP参数，用于记录进日志中
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) arg;
                allParams.add(map);
            }else if(arg instanceof HttpServletRequest){

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

                // 判断该方法是否加了@LoginRequired 注解
                if(method.isAnnotationPresent(LoginRequired.class)){
                    Map<String, String> tokenValue = getHeadersInfo(request);
                    String token = tokenValue.get("logintoken");
                    if(token==null||token.equals("")){
                        try {
                            token=request.getSession().getAttribute("token").toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                            ResponseDTO<String> responseDto=new ResponseDTO<String>();
                            //responseDto.setResult(StatusConstant.INSTANCE.FAILURE);
                            //responseDto.setErrorInfo(StatusConstant.INSTANCE.TOKEN_ERROR);
                            return responseDto;
                        }
                    }

                    //验证token有效性
                    int loginTokenPeriod = 0;//ConfigConstant.INSTANCE.loginTokenPeriod;
                    String userInfo = redisService.get(token);
                    if(userInfo==null)
                    {
                        ResponseDTO<String> responseDto=new ResponseDTO<String>();
                        //responseDto.setResult(StatusConstant.INSTANCE.FAILURE);
                       // responseDto.setErrorInfo(StatusConstant.INSTANCE.TOKEN_ERROR);
                        return responseDto;
                    }
                    redisService.set(token,userInfo);
                    redisService.expire(token,loginTokenPeriod);
                }
                result = pjp.proceed();
            }
        }

        return result;
    }

    //get request headers
    private static Map<String, String> getHeadersInfo(HttpServletRequest request) {
        HashMap<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

}