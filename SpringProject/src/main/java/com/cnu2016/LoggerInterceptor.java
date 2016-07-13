package com.cnu2016;

/**
 * Created by Piyush on 7/11/16.
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter  {
    private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        QueueConnect obj = new QueueConnect();
        long currentTime = System.currentTimeMillis();
        long timeToExecute = currentTime - (long) request.getAttribute("startTime");
        Map<String,String> hashm = new HashMap<String,String>();
        String queryString = request.getQueryString();
        if(queryString != null) {
            String[] pares = queryString.split("&");
            for (String pare : pares) {
                String[] nameAndValue = pare.split("=");
                hashm.put(nameAndValue[0], nameAndValue[1]);
            }
        }
        hashm.put("Request URL", request.getRequestURL().toString());
        hashm.put("Time",request.getAttribute("startTime").toString());
        hashm.put("IP Address",request.getRemoteAddr());
        int responseCode = response.getStatus();
        hashm.put("Response code", responseCode+"");
        hashm.put("ExecuteTime", timeToExecute+"");
        String sends = "Request Url "+request.getRequestURL() + " Time "+request.getAttribute("startTime")
                +" IP Address ="+request.getRemoteAddr() + " Response Code =  " + response.getStatus() ;
        String json = new ObjectMapper().writeValueAsString(hashm);
        obj.sendMessage(json);
    }
}
