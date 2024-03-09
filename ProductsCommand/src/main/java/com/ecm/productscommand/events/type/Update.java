package com.ecm.productscommand.events.type;

import com.ecm.productscommand.events.MessageQueuePublisher;
import com.ecm.productscommand.events.server.MessageQueueServer;

public class Update extends MessageQueuePublisher {
    private final String queueName;

    public Update(MessageQueueServer messageQueueServer, String queueName) {
        super(messageQueueServer);
        this.queueName = queueName;
    }

    @Override
    protected String getQueueName() {
        return queueName;
    }

    @Override
    protected String getEventType() {
        return "UPDATE";
    }
}