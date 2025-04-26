package com.example.demo.controller;

import com.example.demo.cmd.JmsBus;
import com.example.demo.cmd.event.BaseCmd;
import com.example.demo.cmd.sender.Sender;
import com.example.demo.dto.DtoTest;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
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

    private final JmsBus jmsBus;

    @GetMapping("/home")
    public String defaultMethod() {
        return "Called";
    }

    @GetMapping(path = "/location/{locationName}")
    public DtoTest getLocationInfo(@PathVariable String locationName) throws JMSException {
        log.info("getLocationInfo() was called");
        Message message = jmsBus.sendCmd("testQueue", BaseCmd.builder().value(DtoTest.builder().value(locationName).build())
                .build());
        log.info("getLocationInfo() was finished {}", message.getBody(BaseCmd.class).getValue());
        return (DtoTest) message.getBody(BaseCmd.class).getValue();
    }
}
