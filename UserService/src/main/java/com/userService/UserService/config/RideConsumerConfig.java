package com.userService.UserService.config;

import com.userService.UserService.event.RiderAssignmentEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class RideConsumerConfig {
    @Bean
    public ConsumerFactory<String, RiderAssignmentEvent>consumerFactory(){
        Map<String,Object> configProps=new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG,"Ride-service-group");
        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new JsonDeserializer<>(RiderAssignmentEvent.class,false)
        );
    }

    @Bean(name = "riderAssignmentKafkaListenerConsumerFactory")
    public ConcurrentKafkaListenerContainerFactory<String,RiderAssignmentEvent>kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,RiderAssignmentEvent>factory=new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
