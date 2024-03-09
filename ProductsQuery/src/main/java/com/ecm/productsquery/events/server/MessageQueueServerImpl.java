package com.ecm.productsquery.events.server;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageQueueServerImpl implements MessageQueueServer {

    private final String RABBITMQ_HOST = "localhost";
    private final Integer RABBITMQ_PORT = 5672;
    private final String RABBITMQ_USERNAME = "guest";
    private final String RABBITMQ_PASSWORD = "guest";

    private final ConnectionFactory connectionFactory;

    public MessageQueueServerImpl() {
        this.connectionFactory = new ConnectionFactory();

        connectionFactory.setHost(RABBITMQ_HOST);
        connectionFactory.setUsername(RABBITMQ_USERNAME);
        connectionFactory.setPassword(RABBITMQ_PASSWORD);
        connectionFactory.setPort(RABBITMQ_PORT);
    }

    @Override
    public Connection getConnection() throws IOException, TimeoutException {
        return connectionFactory.newConnection();
    }

    @Override
    public Channel getChannel(Connection connection) throws IOException {
        return connection.createChannel();
    }
}
