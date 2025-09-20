package com.mithun.kafka.demo.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String email;

    // getters & setters
}
