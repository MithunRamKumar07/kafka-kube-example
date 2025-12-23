package com.mithun.kafka.demo.service;

import com.mithun.kafka.demo.kafka.model.UserEvent;
import com.mithun.kafka.demo.kafka.producer.EventProducer;
import com.mithun.kafka.demo.mapping.UserMapper;
import com.mithun.kafka.demo.model.UserDTO;
import com.mithun.kafka.demo.mongo.UserDocument;
import com.mithun.kafka.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EventProducer eventProducer;

    public UserDTO processUserEvent(UserDTO user) {
       log.info("Processing user input for : {}", user.getUserId());
        // Add your business logic here
        UserDocument userDocument = UserMapper.INSTANCE.toDocument(user);
        UserEvent userEvent = UserMapper.INSTANCE.toEvent(user);
        userEvent.setEventType("UserEvent");
        userEvent.setTimestamp(System.currentTimeMillis());
        eventProducer.publish("user.topic", "test", userEvent);
        userRepository.save(userDocument);
        log.info("User document successfully inserted into DB : {}", userDocument);
        return user;
    }
}
