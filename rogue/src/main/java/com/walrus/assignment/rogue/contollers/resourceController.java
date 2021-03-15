package com.walrus.assignment.rogue.contollers;

import java.time.LocalTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class resourceController {
    @RequestMapping(value="/available", method=RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> sayHello() {
        LocalTime time = LocalTime.now();
        if (time.getSecond() % 3 == 0)
            return new ResponseEntity<>("Hello World", HttpStatus.OK);
        return new ResponseEntity<>("Server is Down", HttpStatus.GATEWAY_TIMEOUT);
    }
}