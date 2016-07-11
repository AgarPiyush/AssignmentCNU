package com.cnu2016;

/**
 * Created by Piyush on 7/11/16.
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        hashm.put("Request URL", request.getRequestURL().toString());
        hashm.put("Time",request.getAttribute("startTime").toString());
        hashm.put("IP Address",request.getRemoteAddr());
        Integer responseCode = response.getStatus();
        hashm.put("Response code", responseCode.toString());
        hashm.put("ExecuteTime", timeToExecute+"");
        Enumeration headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            hashm.put(key, value);
        }
        String sends = "Request Url "+request.getRequestURL() + " Time "+request.getAttribute("startTime")
                    +" IP Address ="+request.getRemoteAddr() + " Response Code =  " + response.getStatus() ;

        obj.sendMessage(hashm.toString());
    }
}
