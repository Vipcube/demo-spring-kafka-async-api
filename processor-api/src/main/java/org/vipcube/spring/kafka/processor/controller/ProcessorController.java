package org.vipcube.spring.kafka.processor.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vipcube.spring.kafka.domain.ProcessWorkflow;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProcessorController {
  private final ReplyingKafkaTemplate<Long, ProcessWorkflow, ProcessWorkflow> kafkaTemplate;

  @GetMapping("/start")
  public ResponseEntity<?> startWorkflow()
      throws ExecutionException, InterruptedException, TimeoutException {
    var future =
        this.kafkaTemplate.sendAndReceive(
            new ProducerRecord<>("start", ProcessWorkflow.builder().build()));
    var sendResult = future.getSendFuture().get(15, TimeUnit.SECONDS);
    log.info("Sent ok: {}", sendResult.getRecordMetadata());
    var consumerRecord = future.get(15, TimeUnit.SECONDS);
    log.info("Return value: {}", consumerRecord.value());
    return ResponseEntity.ok(consumerRecord.value());
  }
}
