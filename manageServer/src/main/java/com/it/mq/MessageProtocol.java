package com.it.mq;

import java.util.UUID;

/**
 * 处理信息的类
 */
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
