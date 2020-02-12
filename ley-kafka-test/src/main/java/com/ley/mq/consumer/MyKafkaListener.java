package com.ley.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MyKafkaListener {

    @KafkaListener(topics = "${topicName.topic1}")
    public void listenerTwo(ConsumerRecord<?, ?> record) throws Exception {
        log.info("info....record: {}", record);
        log.debug("info....record: {}", record);
        //throw new Exception("waho exception yeah....");
    }
}
