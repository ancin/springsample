package com.springapp.mvc.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @author songkejun
 * @create 2018-01-11 11:19
 **/
public class RabbitmqSend {

    private final static String QUEUE_NAME = "spring-hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        int i=0;
        while (true){
            i++;
            String message = "Hello World!"+i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            Thread.sleep(5000);
        }


       // channel.close();
       // connection.close();
    }

}
