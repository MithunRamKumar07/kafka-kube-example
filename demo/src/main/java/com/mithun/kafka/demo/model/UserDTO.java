package com.mithun.kafka.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    String userId;
    String eventType;
    long timestamp;
    String name;
    String email;
    String address;
    String gender;
    List<String> phoneNumbers;
}
