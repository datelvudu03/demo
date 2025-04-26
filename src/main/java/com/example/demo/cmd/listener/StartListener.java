package com.example.demo.cmd.listener;

import com.example.demo.cmd.JmsBus;
import com.example.demo.cmd.event.BaseCmd;
import com.example.demo.dto.DtoTest;
import com.example.demo.dto.GeocodeResponse;
import com.example.demo.integration.OpenGovernmentCZIntegration;
import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartListener {
    private final JmsBus jmsBus;
    @JmsListener(destination = "testQueue", concurrency = "5-10")
    public void receiveMessage(final Message message) throws JMSException {
        log.info("Received message: {}", message);
        jmsBus.sendReply(message, BaseCmd.builder().value(DtoTest.builder().value("Value0").build()).build());
      /*  jmsTemplate.send(message.getJMSReplyTo(), session -> {
            ObjectMessage responseMsg = session.createObjectMessage();
            responseMsg.setJMSCorrelationID(message.getJMSCorrelationID());
            //responseMsg.setObjectProperty("Object", );
            responseMsg.setObject(DtoTest.builder()
                    .value("Value").build());
            return responseMsg;
        });*/
    }
}
