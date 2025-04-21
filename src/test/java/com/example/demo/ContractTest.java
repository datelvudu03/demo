/*
package com.example.demo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Random;
import java.util.UUID;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class ContractTest {

    private static final String BASE_URI = "http://localhost";

    static {
        System.setProperty("dynamic.management.server.port", String.valueOf(new Random().nextInt(3000) + 25000));
    }

    private static WireMockServer wireMockServer;
    @LocalServerPort
    protected Integer webPort;


    @BeforeEach
    protected void setUp() {
        // Start wiremock server
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8081));
        wireMockServer.start();
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = webPort;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    protected RequestSpecification given() {
        return RestAssured.given()
                .header("X-B3-TraceId", UUID.randomUUID().toString());
    }
}
*/
