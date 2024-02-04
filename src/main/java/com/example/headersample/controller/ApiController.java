package com.example.headersample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@PostMapping("/server-api/sample")
	public void sample(
		@RequestHeader("Content-Length") String contentLength,
		@RequestBody String body
	) {
		logger.info(contentLength);
		logger.info(body);
	}
}
