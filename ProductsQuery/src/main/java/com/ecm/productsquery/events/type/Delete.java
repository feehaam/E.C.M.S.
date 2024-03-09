package com.ecm.productsquery.events.type;

import com.ecm.productsquery.events.core.MessageQueuePublisher;
import com.ecm.productsquery.events.server.MessageQueueServer;

public class Delete extends MessageQueuePublisher {
    private final String queueName;

    public Delete(MessageQueueServer messageQueueServer, String queueName) {
        super(messageQueueServer);
        this.queueName = queueName;
    }

    @Override
    protected String getQueueName() {
        return queueName;
    }

    @Override
    protected String getEventType() {
        return "DELETE";
    }
}