package com.ecm.productsquery.events.triggers;

import com.ecm.productsquery.events.core.Message;
import com.ecm.productsquery.models.Product;
import com.ecm.productsquery.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ProductListener {

    private final ProductService productService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(ProductListener.class);

    @RabbitListener(queues = "products_Q")
    public void listen(Message message) throws IOException {
        String json = objectMapper.writeValueAsString(message.getData());
        if(message.getEventType().equals("DELETE")){
            productService.delete(Integer.valueOf(message.getData().toString()));
            logger.info("Product deleted successfully. " + json);
            return;
        }
        Product product = objectMapper.readValue(json, Product.class);
        Product result;
        if(message.getEventType().equals("CREATE")) {
            result = productService.create(product);
            logger.info("Product created successfully. " + json);
        }
        else{
            result = productService.update(product.getId(), product);
            logger.info("Product updated successfully. " + json);
        }
    }
}
