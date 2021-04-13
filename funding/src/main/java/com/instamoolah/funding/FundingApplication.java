package com.instamoolah.funding;

import com.instamoolah.funding.core.Fund;
import org.apache.kafka.common.header.Headers;
import java.util.stream.StreamSupport;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

@SpringBootApplication
public class FundingApplication {

  private static final Logger logger = LoggerFactory.getLogger(
    FundingApplication.class
  );

  public static void main(String[] args) {
    SpringApplication.run(FundingApplication.class, args);
  }

  @KafkaListener(
    topics = "reserve-funds",
    containerFactory = "kafkaListenerContainerFactory"
  )
  public void reserveFunds(
    ConsumerRecord<String, Fund> cr,
    @Payload Fund fund
  ) {
    logger.info(
      "[RESERVE-FUNDS] Received Payload: {} | Record: {}",
      fund,
      cr.toString()
    );
  }

  @KafkaListener(
    topics = "generate-contract",
    containerFactory = "kafkaListenerContainerFactory"
  )
  public void generateContract(
    ConsumerRecord<String, Fund> cr,
    @Payload Fund fund
  ) {
    logger.info(
      "[GENERATE-CONTRACT] Received Payload: {} | Record: {}",
      fund,
      cr.toString()
    );
  }

  private static String typeIdHeader(Headers headers) {
    return StreamSupport
      .stream(headers.spliterator(), false)
      .filter(header -> header.key().equals("__TypeId__"))
      .findFirst()
      .map(header -> new String(header.value()))
      .orElse("N/A");
  }
}
