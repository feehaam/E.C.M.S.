package com.ecm.productscommand.events.core;

import com.ecm.productscommand.events.server.MessageQueueServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageQueues {
    private static final String PRODUCT_QUEUE = "products_Q";
    public final EventType products;

    @Autowired
    public MessageQueues(MessageQueueServer server) {
        products = new EventType(server, PRODUCT_QUEUE);
    }
}
