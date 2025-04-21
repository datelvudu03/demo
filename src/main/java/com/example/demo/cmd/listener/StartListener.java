package com.example.demo.cmd.listener;

import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartListener {

    @JmsListener(destination = "requestQueue")
    public void onMessage(Message message, Session session) throws JMSException {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String request = textMessage.getText();
            System.out.println("Received request: " + request);

            // Create the reply message
            TextMessage replyMessage = session.createTextMessage("Processed: " + request);

            // Send the reply to the reply queue
            MessageProducer producer = session.createProducer(message.getJMSReplyTo());
            producer.send(replyMessage);
        }
    }
}
