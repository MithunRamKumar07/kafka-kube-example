package com.mithun.kafka.demo.kafka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEvent {

    String userId;
    String eventType;
    long timestamp;
    String name;
    String email;
    String address;
    String gender;
    List<String> phoneNumbers;
}

