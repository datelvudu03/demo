package com.example.demo.cmd.sender;

import com.example.demo.dto.DtoTest;
import com.example.demo.dto.GeocodeResponse;
import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class Sender {

    private final JmsTemplate jmsTemplate;

    public void sendMessage(String destination, String message) throws JMSException {
        log.info("Sending message: {}", message);
        Message received = jmsTemplate.sendAndReceive(destination, session -> session.createTextMessage(message));
        assert received != null;
        log.info("Message received: {}", received.getBody(DtoTest.class));
    }

}
