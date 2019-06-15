package com.it.mq;

import com.google.gson.JsonElement;
import com.it.api.common.util.GsonUtil;
import com.it.api.table.user.Tb_User;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.*;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by wangzy on 2019/6/15.
 */
public class Server implements MessageListener {

    private static int ackMode;
    private static String messageQueueName;
    private static String messageBrokerUrl;

    private Session session;
    private boolean transacted = false;
    private MessageProducer replyProducer;
    private MessageProtocol messageProtocol;

    static {
        messageBrokerUrl = "tcp://localhost:61616";
        messageQueueName = "client.messages";
        ackMode = Session.AUTO_ACKNOWLEDGE;
    }

    public Server() {
        try {
            BrokerService broker = new BrokerService();
            broker.setPersistent(false);
            broker.setUseJmx(false);
            broker.addConnector(messageBrokerUrl);
            broker.start();
        } catch (Exception e) {
            //Handle the exception appropriately
        }

        messageProtocol = new MessageProtocol();
        setupMessageQueueConsumer();
    }

    private void setupMessageQueueConsumer() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(messageBrokerUrl);
        Connection connection;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(transacted, ackMode);
            Destination adminQueue = session.createQueue(messageQueueName);

            replyProducer = session.createProducer(null);
            replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            //Set up a consumer to consume messages off of the admin queue
            MessageConsumer consumer = session.createConsumer(adminQueue);
            consumer.setMessageListener(this);
        } catch (JMSException e) {
            //Handle the exception appropriately
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage response = session.createTextMessage();
            if (message instanceof TextMessage) {
                TextMessage txtMsg = (TextMessage) message;
                String messageText = txtMsg.getText();
                response.setText(messageProtocol.handleProtocolMessage(messageText));
            }

            response.setJMSCorrelationID(message.getJMSCorrelationID());

            replyProducer.send(message.getJMSReplyTo(), response);
        } catch (JMSException e) {
            //Handle the exception appropriately
        }
    }

    //单独的处理信息的类
    public class MessageProtocol {
        public String handleProtocolMessage(String messageText) {
            String responseText;
            if ("MyProtocolMessage".equalsIgnoreCase(messageText)) {
                String s = UUID.randomUUID().toString();
                responseText = "---服务器响应内容" + s;
            } else {
                responseText = "Unknown protocol message: " + messageText;
            }
            System.out.println(responseText);
            return responseText;
        }
    }
}
