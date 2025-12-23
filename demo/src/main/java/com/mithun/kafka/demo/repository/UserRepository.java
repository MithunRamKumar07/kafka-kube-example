package com.mithun.kafka.demo.repository;

import com.mithun.kafka.demo.mongo.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends MongoRepository<UserDocument, String> {
    UserDocument findByEmail(String email);
}