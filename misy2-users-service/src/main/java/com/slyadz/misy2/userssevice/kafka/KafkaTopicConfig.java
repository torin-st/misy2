package com.slyadz.misy2.userssevice.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
class KafkaTopicConfig {

	@Value("${kafka.bootstrap-servers}")
	private String bootstrapServers;
	@Value("${kafka.topics.users.name}")
	private String usersTopic;

	@Bean
	public KafkaAdmin admin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		return new KafkaAdmin(configs);
	}

	@Bean
	NewTopic usersTopic() {
		return TopicBuilder.name(usersTopic).build();
	}
}
