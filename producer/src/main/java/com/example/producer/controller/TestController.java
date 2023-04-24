package com.example.producer.controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.producer.model.Person;

@RestController
public class TestController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Autowired
    private KafkaTemplate<String, Serializable> jsonKafkaTemplate;

    @GetMapping("send")
    public void send() {
        kafkaTemplate.send("topic-1", "Ola mundo");
    }
    
    @GetMapping("send-person")
    public void sendPerson() {
        jsonKafkaTemplate.send("person-topic", new Person("João", new Random().nextInt(50)));
    }
}