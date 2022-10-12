package org.vipcube.spring.kafka.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class ProcessorApplication {
  public static void main(String[] args) {
    SpringApplication.run(ProcessorApplication.class, args);
  }
}
