package com.example.producer.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

@Configuration
public class ProducerKafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        var configs = new HashMap<String, Object>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configs);
    }

//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
    
    @Bean
    public RoutingKafkaTemplate routingTemplate(GenericApplicationContext context, 
    											ProducerFactory producerFactory) {
    	var jsonProducerFactory = jsonProducerFactory();
    	context.registerBean(DefaultKafkaProducerFactory.class, "jsonPF", jsonProducerFactory);
    	
    	Map<Pattern, ProducerFactory<Object, Object>> map = new LinkedHashMap<>();
    	map.put(Pattern.compile("topic-.*"), producerFactory);
    	map.put(Pattern.compile(".*-topic"), jsonProducerFactory);
    	return new RoutingKafkaTemplate(map);
    }
    
//    @Bean
    public ProducerFactory jsonProducerFactory() {
        var configs = new HashMap<String, Object>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new JsonSerializer<>());
    }
    
//    @Bean
//    public KafkaTemplate<String, Serializable> jsonKafkaTemplate() {
//        return new KafkaTemplate(jsonProducerFactory());
//    }


    //criando um topico a partir da propria aplicacao
    @Bean
    public KafkaAdmin kafkaAdmin() {
        var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }

//    @Bean
//    public NewTopic topic1() {
//        return new NewTopic("topic-1", 2, Short.valueOf("1"));
//    }

//  Cria topicos com configs a partir do brocker
//
//  @Bean
//  public NewTopic topic1() {
////    return TopicBuilder.name("topic-1").build();
//  }

//  Cria diversos topicos a partir de uma bean
//  
    @Bean
	public KafkaAdmin.NewTopics topics() {
    	return new KafkaAdmin.NewTopics(
    			TopicBuilder.name("topic-1").partitions(2).replicas(1).build(),
//    			TopicBuilder.name("my-topic").partitions(10).build(),
	            TopicBuilder.name("person-topic").partitions(2).build(),
	            TopicBuilder.name("person-topic.DLT").partitions(2).build(),
	            TopicBuilder.name("city-topic").partitions(2).build()
	    );
	}
}