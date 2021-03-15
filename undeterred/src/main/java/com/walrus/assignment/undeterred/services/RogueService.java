package com.walrus.assignment.undeterred.services;

import org.springframework.http.ResponseEntity;

public interface RogueService {

    public ResponseEntity<String> getResource(int retryCount, long waitTime);
}
