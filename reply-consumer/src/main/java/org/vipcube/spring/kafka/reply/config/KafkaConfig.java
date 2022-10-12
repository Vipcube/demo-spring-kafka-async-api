package org.vipcube.spring.kafka.reply.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
  @Bean
  public NewTopic step2() {
    return TopicBuilder.name("step2").partitions(1).replicas(1).build();
  }
}
