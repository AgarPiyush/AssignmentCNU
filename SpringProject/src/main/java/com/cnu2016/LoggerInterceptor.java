package com.cnu2016;

/**
 * Created by Piyush on 7/11/16.
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter  {
    private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);
    @Autowired
    QueueConnect obj;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    /**
     * Send logs to AWS queue. Message includes parameters, request url, time,
     * ip address, response code, time, execute time, and request type. The logs
     * are pushed to database using python script from SQS Queue
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long currentTime = System.currentTimeMillis();
        long timeToExecute = currentTime - (long) request.getAttribute("startTime");
        Map<String,String> hashm = new HashMap<String,String>();
        String queryString = request.getQueryString();
        hashm.put("Parameters",queryString);
        hashm.put("RequestURL", request.getRequestURL().toString());
        hashm.put("Time",request.getAttribute("startTime").toString());
        hashm.put("IPAddress",request.getRemoteAddr());
        int responseCode = response.getStatus();
        hashm.put("ResponseCode", responseCode+"");
        hashm.put("ExecuteTime", timeToExecute+"");
        hashm.put("RequestType",request.getMethod());
        String json = new ObjectMapper().writeValueAsString(hashm);
        obj.sendMessage(json);
    }
}
