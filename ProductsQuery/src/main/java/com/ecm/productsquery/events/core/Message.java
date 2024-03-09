package com.ecm.productsquery.events.core;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Message {
    private String eventType;
    private LocalDateTime executionTime;
    private Object data;
}
