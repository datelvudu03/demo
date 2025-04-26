package com.example.demo.cmd;

import com.example.demo.cmd.event.BaseCmd;
import com.example.demo.dto.DtoTest;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JmsBus {

    private final JmsTemplate jmsTemplate;

    public void sendTextMessage(String destination, String message) throws JMSException {
        log.info("Sending message: {}", message);
        Message received = jmsTemplate.sendAndReceive(destination, session -> session.createTextMessage(message));
        assert received != null;
        log.info("Message received: {}", received.getBody(DtoTest.class));
    }

    public Message sendCmd(String destination, BaseCmd cmd){
        //responseMsg.setJMSCorrelationID(message.getJMSCorrelationID());
        return jmsTemplate.sendAndReceive(destination, session -> {
            ObjectMessage responseMsg = session.createObjectMessage();
            //responseMsg.setJMSCorrelationID(message.getJMSCorrelationID());
            responseMsg.setObject(cmd);
            return responseMsg;
        });
    }

    public void sendReply(Message message, BaseCmd cmd) throws JMSException {
        //responseMsg.setJMSCorrelationID(message.getJMSCorrelationID());
        jmsTemplate.send(message.getJMSReplyTo(), session -> {
            ObjectMessage responseMsg = session.createObjectMessage();
            responseMsg.setJMSCorrelationID(message.getJMSCorrelationID());
            responseMsg.setObject(cmd);
            return responseMsg;
        });
    }
}
