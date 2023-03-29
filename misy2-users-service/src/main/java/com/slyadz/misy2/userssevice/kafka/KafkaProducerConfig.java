package com.slyadz.misy2.userssevice.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.slyadz.misy2.usersservice.api.event.UserVerificationFailedEventData;
import com.slyadz.misy2.usersservice.api.event.UserVerificationSuccessEventData;

@Configuration
class KafkaProducerConfig {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Value("${kafka.bootstrap-servers}")
	private String bootstrapServers;
	@Value("${kafka.topics.users.name}")
	private String usersTopic;

	@Bean
	Map<String, Object> stringProducerConfigs() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return properties;
	}

	@Bean
	ProducerFactory<String, String> stringProducerFactory() {
		return new DefaultKafkaProducerFactory<>(stringProducerConfigs());
	}

	@Bean
	KafkaTemplate<String, String> stringKafkaTemplate() {
		KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(stringProducerFactory());
		kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
		kafkaTemplate.setDefaultTopic(usersTopic);
		kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
			@Override
			public void onSuccess(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata) {
				logger.info("ACK from ProducerListener message: {} offset:  {}", producerRecord.value(),
						recordMetadata.offset());
			}
		});
		return kafkaTemplate;
	}

	private Map<String, Object> userVerificationFailedEventDataProducerConfig() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return properties;
	}

	@Bean
	ProducerFactory<String, UserVerificationFailedEventData> userVerificationFailedEventDataProducerFactory() {
		return new DefaultKafkaProducerFactory<>(userVerificationFailedEventDataProducerConfig());
	}

	@Bean
	KafkaTemplate<String, UserVerificationFailedEventData> userVerificationFailedEventDataKafkaTemplate() {
		KafkaTemplate<String, UserVerificationFailedEventData> kafkaTemplate = new KafkaTemplate<>(userVerificationFailedEventDataProducerFactory());
		return kafkaTemplate;
	}

	private Map<String, Object> userVerificationSuccessEventDataProducerConfig() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return properties;
	}

	@Bean
	ProducerFactory<String, UserVerificationSuccessEventData> userVerificationSuccessEventDataProducerFactory() {
		return new DefaultKafkaProducerFactory<>(userVerificationSuccessEventDataProducerConfig());
	}

	@Bean
	KafkaTemplate<String, UserVerificationSuccessEventData> userVerificationSuccessEventDataKafkaTemplate() {
		KafkaTemplate<String, UserVerificationSuccessEventData> kafkaTemplate = new KafkaTemplate<>(userVerificationSuccessEventDataProducerFactory());
		return kafkaTemplate;
	}

}