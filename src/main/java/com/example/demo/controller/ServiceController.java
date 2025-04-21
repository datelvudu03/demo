package com.example.demo.controller;

import com.example.demo.cmd.sender.Sender;
import jakarta.jms.JMSException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ServiceController {

    private final Sender sender;

    @GetMapping("/home")
    public String defaultMethod() {
        return "Called";
    }

    @GetMapping(path = "/location/{locationName}")
    public String getLocationInfo(@PathVariable String locationName) throws JMSException {
        log.info("getLocationInfo() was called");
        sender.sendAndReceive("requestQueue", "getLocationInfo", locationName);
        return null;
    }
}
