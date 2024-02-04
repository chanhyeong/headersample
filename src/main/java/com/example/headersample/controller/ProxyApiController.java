package com.example.headersample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/client-proxy")
public class ProxyApiController {

	private static final Logger logger = LoggerFactory.getLogger(ProxyApiController.class);

	@PostMapping("/**")
	public ResponseEntity<?> serverProxy(
		ProxyExchange<byte[]> proxyExchange,
		HttpServletRequest request
	) {
		String url = assembleUrl(proxyExchange, request);
		return proxyExchange.uri(url).post();
	}

	private String assembleUrl(
		ProxyExchange<byte[]> proxyExchange,
		HttpServletRequest request
	) {
		String replacedPath = proxyExchange.path().replace("/client-proxy", "");
		StringBuilder urlBuilder = new StringBuilder("http://localhost:8080");
		urlBuilder.append(replacedPath);
		if (StringUtils.hasLength(request.getQueryString())) {
			urlBuilder.append("?").append(request.getQueryString());
		}
		return urlBuilder.toString();
	}
}
