package org.vipcube.spring.kafka.processor.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.vipcube.spring.kafka.domain.ProcessWorkflow;

@Configuration
public class KafkaConfig {
  @Bean
  public ReplyingKafkaTemplate<Long, ProcessWorkflow, ProcessWorkflow> replyingTemplate(
      ProducerFactory<Long, ProcessWorkflow> pf,
      ConcurrentMessageListenerContainer<Long, ProcessWorkflow> repliesContainer) {
    return new ReplyingKafkaTemplate<>(pf, repliesContainer);
  }

  @Bean
  public ConcurrentMessageListenerContainer<Long, ProcessWorkflow> repliesContainer(
      ConcurrentKafkaListenerContainerFactory<Long, ProcessWorkflow> containerFactory) {

    ConcurrentMessageListenerContainer<Long, ProcessWorkflow> repliesContainer =
        containerFactory.createContainer("reply");
    repliesContainer.getContainerProperties().setGroupId("repliesGroup");
    repliesContainer.setAutoStartup(false);
    return repliesContainer;
  }

  @Bean
  public NewTopic start() {
    return TopicBuilder.name("start").partitions(1).replicas(1).build();
  }

  @Bean
  public NewTopic reply() {
    return TopicBuilder.name("reply").partitions(1).replicas(1).build();
  }
}
