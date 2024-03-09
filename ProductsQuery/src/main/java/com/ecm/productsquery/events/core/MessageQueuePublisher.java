package com.ecm.productsquery.events.core;

import com.ecm.productsquery.events.server.MessageQueueServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

public abstract class MessageQueuePublisher {
    private final MessageQueueServer rabbitMqServer;
    private final Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();

    protected MessageQueuePublisher(MessageQueueServer rabbitMqServer) {
        this.rabbitMqServer = rabbitMqServer;
    }

    protected abstract String getQueueName();
    protected abstract String getEventType();

    public void publish(Object data) {

        Message message = Message.builder()
                .data(data)
                .executionTime(LocalDateTime.now())
                .eventType(getEventType())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String jsonData;
        try{
            jsonData = mapper.writeValueAsString(message);
        }
        catch (IOException e){
            jsonData = "";
        }

        try (Connection connection = rabbitMqServer.getConnection()) {
            try (Channel channel = rabbitMqServer.getChannel(connection)) {
                channel.queueDeclare(getQueueName(), true, false, false, null);
                channel.basicPublish("", getQueueName(), null, jsonData.getBytes());
            }
        } catch (IOException | TimeoutException e) {
            System.out.println(e.getMessage());
        }
    }
}
