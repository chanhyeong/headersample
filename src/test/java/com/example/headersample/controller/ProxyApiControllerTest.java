package com.example.headersample.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.with;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest
class ProxyApiControllerTest {
	@BeforeEach
	void setUp(@Autowired WebApplicationContext context) {
		RestAssuredMockMvc.webAppContextSetup(context);
		RestAssuredMockMvc.resultHandlers(MockMvcResultHandlers.print(System.out));
	}

	@Test
	void failedBecauseOfInvalidContentLengthHeader() {
		with()
			.body( // space-indented json
				"""
					{
					    "contextContent": {
					        "aSource": {
					            "a": "20242024"
					        },
					        "bSource": {
					            "b": "20252025"
					        }
					    }
					}
					"""
			)
			.headers(Map.of("Content-Type", "application/json"))
			.when()
			.post("/client-proxy/server-api/sample")
			.then()
			.assertThat()
			.statusCode(200);
	}

	@Test
	void successWithMatchingContentLength() {
		with()
			.body( // space-indented json
				"""
					{
					    "contextContent": {
					        "aSource": {
					            "a": "20242024"
					        },
					        "bSource": {
					            "b": "20252025"
					        }
					    }
					}
					"""
			)
			.headers(
				Map.of(
					"Content-Type", "application/json",
					"Content-Length", "125"
				)
			)
			.when()
			.post("/client-proxy/server-api/sample")
			.then()
			.assertThat()
			.statusCode(200);
	}

	@AfterEach
	void tearDown() {
		RestAssuredMockMvc.reset();
	}
}
