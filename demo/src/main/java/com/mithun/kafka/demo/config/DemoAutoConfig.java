/*
package com.mithun.kafka.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaConnectionDetails;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.autoconfigure.kafka.SslBundleSslEngineFactory;
import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.StringUtils;

import java.util.Map;

@Slf4j
@AutoConfiguration
@AutoConfigureBefore(KafkaAutoConfiguration.class)
public class DemoAutoConfig {
    // Configure all the common configs, like the restTemplate , jdbc template kafka template etc ObjectMapper etc
    // Mostly this will be done for projects that are exported as libraries and used by multiple projects
    @Bean
    @ConditionalOnMissingBean(ProducerFactory.class)
    @ConditionalOnProperty(name = "spring.kafka.bootstrap-servers", matchIfMissing = true)

    public DefaultKafkaProducerFactory<String, Object> producerFactory( KafkaConnectionDetails kafkaConnectionDetails, KafkaProperties kafkaProperties, ObjectMapper objectMapper, SslBundles sslBundles) {
        configureSslBundle(kafkaProperties, sslBundles);
        var producerProperties = kafkaProperties.buildProducerProperties();
        applyKafkaConnectionDetailsForProducer(producerProperties, kafkaConnectionDetails);

        return new DefaultKafkaProducerFactory<>(
                producerProperties,
                new StringSerializer(),
                new JsonSerializer<>(objectMapper) // Default Serializer. If the event has another other parent class, we can implement a custom serializer
        );
    }

    private static void configureSslBundle(KafkaProperties kafkaProperties, SslBundles sslBundles) {
        String sslBundleName = null;
        try {
            sslBundleName = sslBundles.getBundleNames().getFirst();
        } catch (Exception e) {
            log.atInfo().log("No SslBundle available for creating kafkaProducerFactory," +
                    " consider adding an Ssl bundle to your configuration");
        }
        kafkaProperties.getSsl().setBundle(sslBundleName);
    }

    private void applyKafkaConnectionDetailsForProducer(Map<String, Object> properties, KafkaConnectionDetails connectionDetails) {
        KafkaConnectionDetails.Configuration producer = connectionDetails.getProducer();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); //producer.getBootstrapServers());
        applySecurityProtocol(properties, producer.getSecurityProtocol());
        applySslBundle(properties, producer.getSslBundle());
    }

    private static void applySslBundle(Map<String, Object> properties, SslBundle sslBundle) {
        if (sslBundle != null) {
            properties.put(SslConfigs.SSL_ENGINE_FACTORY_CLASS_CONFIG, SslBundleSslEngineFactory.class);
            properties.put(SslBundle.class.getName(), sslBundle);
        }
    }

    private static void applySecurityProtocol(Map<String, Object> properties, String securityProtocol) {
        if (StringUtils.hasLength(securityProtocol)) {
            properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
        }
    }

}
*/
