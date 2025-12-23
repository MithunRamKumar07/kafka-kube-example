package com.mithun.kafka.demo.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(String topic, String key, Object event) {

        ProducerRecord<String, Object> record = new ProducerRecord<>(topic,key,event);
        record.headers().add("origin", "JAVA_APP".getBytes());
        record.headers().add("key", key.getBytes());
        record.headers().add("Authorization", "Bearer some-token".getBytes());
        record.headers().add(KafkaHeaders.TOPIC, topic.getBytes());

        kafkaTemplate.send(record)
                .thenAccept(result -> {
                    log.info("Message sent successfully to topic: {}, partition: {}, offset: {}",
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());
                })
                .exceptionally(exception -> {;
                    log.error("Failed to send message to topic: {}", topic, exception);
                    return null;
                });
    }
}

