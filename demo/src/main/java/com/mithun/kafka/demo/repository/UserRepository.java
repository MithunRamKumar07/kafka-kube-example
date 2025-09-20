package com.mithun.kafka.demo.repository;

import com.mithun.kafka.demo.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}