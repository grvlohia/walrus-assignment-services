package com.walrus.assignment.undeterred.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RogueServiceImpl implements RogueService{

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> getResource(int retryCount, long waitTime) {
        String url = "http://localhost:5000/available";
        //Setting retry count and wait time for the request in request Headers
        // this is done for passing updates to the client
        HttpHeaders headers = new HttpHeaders();
        headers.set("RETRY_COUNT", Integer.toString(retryCount));
        headers.set("WAIT_TIME", Long.toString(waitTime));
        // Builing request
        HttpEntity request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        return response;
    }
    
}
