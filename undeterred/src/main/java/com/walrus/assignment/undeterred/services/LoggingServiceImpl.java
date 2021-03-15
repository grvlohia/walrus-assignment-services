package com.walrus.assignment.undeterred.services;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingServiceImpl implements LoggingService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> parameters = buildParametersMap(httpServletRequest);
        Map<String, String> headers = buildHeadersMap(httpServletRequest);
        stringBuilder.append("REQUEST ");
        stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
        stringBuilder.append("headers=[").append(headers).append("] ");

        if (!parameters.isEmpty()) {
            stringBuilder.append("parameters=[").append(parameters).append("] ");
        }

        if (body != null) {
            stringBuilder.append("body=[" + body + "]");
        }

        logger.info(stringBuilder.toString());
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();

        while(headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = httpServletRequest.getHeader(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while(parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    // @Override
    // public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
    //         Object body) {
    //     // TODO Auto-generated method stub
        
    // }
    
}
