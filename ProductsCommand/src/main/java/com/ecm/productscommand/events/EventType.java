package com.ecm.productscommand.events;

import com.ecm.productscommand.events.server.MessageQueueServer;
import com.ecm.productscommand.events.type.*;
import com.ecm.productscommand.events.type.Error;

public class EventType {
    public final MessageQueuePublisher create;
    public final MessageQueuePublisher update;
    public final MessageQueuePublisher delete;
    public final MessageQueuePublisher info;
    public final MessageQueuePublisher error;

    public EventType(MessageQueueServer messageQueueServer, String queueName){
        create = new Create(messageQueueServer, queueName);
        update = new Update(messageQueueServer, queueName);
        delete = new Delete(messageQueueServer, queueName);
        info = new Info(messageQueueServer, queueName);
        error = new Error(messageQueueServer, queueName);
    }
}
