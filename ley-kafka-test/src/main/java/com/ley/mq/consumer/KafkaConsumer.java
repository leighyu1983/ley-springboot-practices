package com.ley.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    /**
     * 监听topic1主题,单条消费
     */
    @KafkaListener(topics = "${topicName.topic1}")
    public void listenTopicOne(ConsumerRecord<String, String> record) {
        log.info("info....topic: {}, value: {}", record.topic(), record.value());
        log.debug("debug....topic: {}, value: {}", record.topic(), record.value());
    }
}
