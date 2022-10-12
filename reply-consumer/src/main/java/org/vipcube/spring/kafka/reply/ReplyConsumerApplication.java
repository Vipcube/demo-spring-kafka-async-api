package org.vipcube.spring.kafka.reply;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.vipcube.spring.kafka.domain.ProcessWorkflow;

@Slf4j
@EnableKafka
@SpringBootApplication
public class ReplyConsumerApplication {
  public static void main(String[] args) {
    SpringApplication.run(ReplyConsumerApplication.class, args);
  }

  @KafkaListener(id = "step1", topics = "start")
  @SendTo("step2")
  public ProcessWorkflow step1(ProcessWorkflow workflow) {
    log.info("Trigger Step1");
    workflow.setStep1(true);
    return workflow;
  }

  @KafkaListener(id = "step2", topics = "step2")
  @SendTo("reply") // use default replyTo expression
  public ProcessWorkflow step2(ProcessWorkflow workflow) {
    log.info("Trigger Step2");
    workflow.setStep2(true);
    return workflow;
  }
}
