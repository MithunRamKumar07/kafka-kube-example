package com.mithun.kafka.demo.repository;

import com.mithun.kafka.demo.mongo.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserReactiveRepository extends MongoRepository<UserDocument, String> {
}
