package com.log.centralizedlogging.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic-json.name}")
    private String topicJsonName;


    @Bean
    public NewTopic kafkaLogMessagesJsonTopic(){
        return TopicBuilder.name(topicJsonName)
                .build();
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
