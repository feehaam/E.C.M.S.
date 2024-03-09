package com.ecm.productscommand.events.type;

import com.ecm.productscommand.events.MessageQueuePublisher;
import com.ecm.productscommand.events.server.MessageQueueServer;

public class Create extends MessageQueuePublisher {
    private final String queueName;

    public Create(MessageQueueServer messageQueueServer, String queueName) {
        super(messageQueueServer);
        this.queueName = queueName;
    }

    @Override
    protected String getQueueName() {
        return queueName;
    }

    @Override
    protected String getEventType() {
        return "CREATE";
    }
}