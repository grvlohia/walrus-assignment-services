package com.walrus.assignment.undeterred.interceptors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import com.walrus.assignment.undeterred.models.RetryUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        logger.debug("[RestTemplateInterceptor] Request body: {}", new String(body, StandardCharsets.UTF_8));
        ClientHttpResponse response = execution.execute(request, body);
        InputStreamReader isr = new InputStreamReader(response.getBody(), StandardCharsets.UTF_8);
        String resBody = new BufferedReader(isr).lines().collect(Collectors.joining("\n"));
        //creating an update for the WebSocket
        HttpHeaders headers = request.getHeaders();
        int retryCount = Integer.parseInt(headers.get("RETRY_COUNT").get(0));
        long waitTime = Long.parseLong(headers.get("WAIT_TIME").get(0));
        int statusCode = response.getStatusCode().value();
        RetryUpdate update = new RetryUpdate(retryCount, waitTime, resBody, statusCode);
        template.convertAndSend("/topic/updates", update);
        logger.debug("[RestTemplateInterceptor] Response body: {}", resBody);
        return response;
    }

}
