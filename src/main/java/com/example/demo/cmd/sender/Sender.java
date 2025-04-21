package com.example.demo.cmd.sender;

import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class Sender {

    private final ConnectionFactory connectionFactory;

    public String sendAndReceive(String destination, String reply ,String messageContent) throws JMSException {
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {

            // Create producer for request queue
            MessageProducer producer = session.createProducer(new ActiveMQQueue(destination));

            // Create a text message
            TextMessage message = session.createTextMessage(messageContent);
            message.setJMSReplyTo(new ActiveMQQueue(reply));

            // Send the message
            producer.(message);

            // Create consumer for reply queue
            MessageConsumer consumer = session.createConsumer(new ActiveMQQueue(reply));

            // Wait for the reply
            Message replyMessage = consumer.receive(10000); // Timeout in 5 seconds
            if (replyMessage instanceof TextMessage) {
                return ((TextMessage) replyMessage).getText();
            } else {
                throw new JMSException("No reply received or invalid message type");
            }
        }
    }

}
