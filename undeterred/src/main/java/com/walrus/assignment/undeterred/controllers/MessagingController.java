package com.walrus.assignment.undeterred.controllers;


import com.walrus.assignment.undeterred.models.RetryUpdate;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessagingController {

    @MessageMapping("/message")
    @SendTo("/topic/updates")
    public RetryUpdate sendUpdate(RetryUpdate update) {
        return update;
    }
}
