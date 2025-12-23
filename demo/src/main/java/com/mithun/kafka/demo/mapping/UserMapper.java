package com.mithun.kafka.demo.mapping;

import com.mithun.kafka.demo.kafka.model.UserEvent;
import com.mithun.kafka.demo.model.UserDTO;
import com.mithun.kafka.demo.mongo.UserDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDocument toDocument(UserDTO user);

    UserDTO toDTO(UserDocument userDocument);

    @Mapping(target = "eventType", expression = "java(\"UserEvent\")")
    @Mapping(target = "timestamp", expression = "java(System.currentTimeMillis())")
    UserEvent toEvent(UserDTO user);

    @Named("mapEventType")
    default String mapEventType() {
        return "UserEvent";
    }

}

