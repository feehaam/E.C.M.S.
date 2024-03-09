package com.ecm.productscommand.events;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Message {
    private String eventType;
    public LocalDateTime executionTime;
    private Object data;
}
