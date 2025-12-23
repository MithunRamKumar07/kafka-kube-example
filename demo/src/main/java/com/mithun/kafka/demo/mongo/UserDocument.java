package com.mithun.kafka.demo.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Document(collection = "users")
public class UserDocument {
    @Id
    private String userId;
    private String name;
    private String email;
}
