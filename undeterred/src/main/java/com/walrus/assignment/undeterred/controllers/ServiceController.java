package com.walrus.assignment.undeterred.controllers;

import com.walrus.assignment.undeterred.services.RogueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;

@Controller
public class ServiceController {

	// private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RogueService rogueService;

	// setting parameters to control the backoff algorithm
	private static final int MAX_RETRIES = 10;
	private static final long MAX_WAIT_INTERVAL = 15000;

	@CrossOrigin
	@RequestMapping(value = "/getresource", method = RequestMethod.GET)
	@ResponseBody
	public String callRogueApplication() {
		int retries = 0;
		boolean retry = false;
		do {
			long waitTime = Math.min(getWaitTimeExp(retries), MAX_WAIT_INTERVAL);
			System.out.println("Wait time: " + waitTime + "ms");
			try {
				Thread.sleep(waitTime);
				var nextWaitTime = Math.min(getWaitTimeExp(retries + 1), MAX_WAIT_INTERVAL);
				ResponseEntity<String> response = rogueService.getResource(retries, nextWaitTime);
				if (response.getStatusCode() == HttpStatus.OK) {
					return response.getBody();
				}
			} catch (IllegalArgumentException | InterruptedException e) {
                System.out.println("Error sleeping thread: " + e.getMessage());
            } catch (HttpServerErrorException e) {
				retry = true;
				retries++;
			}
		} while (retry && retries < MAX_RETRIES);
		return String.format("Failed to get resource after %d retries", retries);
	}

	public static long getWaitTimeExp(int retryCount) {
		if (retryCount == 0)
			return 0;
		long waitTime = (long) Math.pow(2, retryCount) * 100L;
		return waitTime;
	}
}
