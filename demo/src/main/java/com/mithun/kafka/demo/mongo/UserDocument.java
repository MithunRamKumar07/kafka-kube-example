package com.mithun.kafka.demo.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@Data
@Document(collection = "users")
public class UserDocument {
    @Id
    String userId;
    String eventType;
    long timestamp;
    String name;
    String email;
    String address;
    String gender;
    List<String> phoneNumbers;
}
