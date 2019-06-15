package com.it.client.mqTest;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.*;
import java.util.Random;
import java.util.UUID;

/**
 * Created by wangzy on 2019/6/15.
 */
public class Client implements MessageListener {

    private static int ackMode;
    private static String clientQueueName;
    private boolean transacted = false;
    private MessageProducer producer;

    static {
        clientQueueName = "client.messages";
        ackMode = Session.AUTO_ACKNOWLEDGE;
    }

    public Client() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(transacted, ackMode);
            Destination adminQueue = session.createQueue(clientQueueName);

            this.producer = session.createProducer(adminQueue);
            this.producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            Destination tempDest = session.createTemporaryQueue();
            MessageConsumer responseConsumer = session.createConsumer(tempDest);

            responseConsumer.setMessageListener(this);

            TextMessage txtMessage = session.createTextMessage();
            txtMessage.setText("MyProtocolMessage");

            txtMessage.setJMSReplyTo(tempDest);

            String correlationId = this.createRandomString();
            txtMessage.setJMSCorrelationID(correlationId);
            this.producer.send(txtMessage);
        } catch (JMSException e) {
            //Handle the exception appropriately
        }
    }

    private String createRandomString() {
        Random random = new Random(System.currentTimeMillis());
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("相应内容 = " + textMessage.getText());
            }
        } catch (JMSException e) {
            //Handle the exception appropriately
        }
    }

}
