package com.example.consumer.listener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TestListener {
	
	//KafkaHeaders.OFFSET
	//KafkaHeaders.RECEIVED_MESSAGE_KEY
	//KafkaHeaders.RECEIVED_TOPIC
	//KafkaHeaders.RECEIVED_PARTITION_ID
	//KafkaHeaders.RECEIVED_TIMESTAMP
	//KafkaHeaders.TIMESTAMP_TYPE
	
//	Recebendo header por header
//	@KafkaListener(topics = "topic-1", groupId = "group-1")
//	public void Listen(String message,
//			@Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//			@Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
//		  log.info("Thread: {}", Thread.currentThread().getId());
//		log.info("Topic {} Pt {}: {}", topic, partition, message);
//	}
	
	@KafkaListener(topics = "topic-1", groupId = "group-1")
	public void Listen(String message, ConsumerRecordMetadata metadata) {
//		  log.info("Thread: {}", Thread.currentThread().getId());
		log.info("Topic {} Pt {} Offset {}: {}", metadata.topic(), metadata.partition(), metadata.offset(), message);
		log.info("Timestamp {}", LocalDateTime.ofInstant(
				Instant.ofEpochMilli(metadata.timestamp()), TimeZone.getDefault().toZoneId())
				);
	}
	

}