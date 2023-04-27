package com.example.consumer.listener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.example.consumer.model.City;
import com.example.consumer.model.Person;

import custom.PersonCustomListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TestListener {
	
//	@KafkaListener(topics = "topic-1", groupId = "group-1")
//    public void listen(String message) {
//        log.info("Thread: {} Message: {}", Thread.currentThread().getId(), message);
//   }
	
	@KafkaListener(topics = "topic-1", groupId = "group-1")
    public void listen(List<String> messages) {
        log.info("Thread: {} Messages: {}", Thread.currentThread().getId(), messages);
   }
	
//	@KafkaListener(topics = "my-topic", groupId = "my-group")
//	public void Listen2(String message) {
//		log.info("Thread: {} Message: {}", Thread.currentThread().getId(), message);
//	}
	
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
	
//	@KafkaListener(topics = "topic-1", groupId = "group-1")
//	public void Listen(String message, ConsumerRecordMetadata metadata) {
//		  log.info("Thread: {}", Thread.currentThread().getId());
//		log.info("Topic {} Pt {} Offset {}: {}", metadata.topic(), metadata.partition(), metadata.offset(), message);
//		log.info("Timestamp {}", LocalDateTime.ofInstant(
//				Instant.ofEpochMilli(metadata.timestamp()), TimeZone.getDefault().toZoneId())
//				);
//	}
	
//	@KafkaListener(topicPartitions = {@TopicPartition(topic = "my-topic", partitions = "0")}, groupId = "my-group")
//	public void listen2(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
//		log.info("Partition 0: {} Message: {}", partition, message);
//	}
	
//	@KafkaListener(topicPartitions = {@TopicPartition(topic = "my-topic", partitions = "1-9")}, groupId = "my-group")
//	public void Listen3(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
//		log.info("Partition 1-9: {} Message: {}", partition, message);
//	}
	
	@PersonCustomListener(groupId = "group-1")
    public void create(Person person) {
//        log.info("Thread: {}", Thread.currentThread().getId());
        log.info("Criar pessoa: {}", person);
        throw new IllegalArgumentException("Teste");
    }
	
	@KafkaListener(topics = "city-topic", groupId = "group-1", containerFactory = "jsonKafkaListenerContainerFactory")
    public void create(List<Message<City>> messages) {
//       log.info("Criar cidade: {}", city);
//		 log.info("Cidades: {}", cities);
//		 log.info("Particoes: {}", partitions;
		log.info("Messages: {}", messages);
		var city = messages.get(0).getPayload();
		log.info("Cidades: {}", city);
		log.info("Headers: {}", messages.get(0).getHeaders());
		
    }
	
//	@PersonCustomListener(groupId = "group-2")
//	public void history(Person person) {
//		log.info("Thread: {}", Thread.currentThread().getId());
//		log.info("Histórico: {}", person);
//	}


}