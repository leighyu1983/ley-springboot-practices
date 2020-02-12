package com.ley.mq.consumer;

import com.ley.configurationProperty.MyKafkaProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author Leigh Yu
 * @date 2020/2/11 19:45
 *
 */
@Component
@Slf4j
// this is not good design, handle kafkaCOnsumer is not thread safe issue.
//@Scope("prototype")
public class MyKafkaConsumerService {

    @Autowired private MyKafkaProperty myKafkaProperty;
    private KafkaConsumer<String, Object> kafkaConsumer;

    /**
     * 读取配置，订阅topic, 返回consumer对象
     * @param
     * @return
     */
    @PostConstruct
    private void getConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", myKafkaProperty.getBootstrapServers());
        props.put("group.id", myKafkaProperty.getRetryGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 1800000);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 1800000);
        kafkaConsumer = new KafkaConsumer<String, Object>(props);
    }

    public void getRecord(String topic, int partition, long offset) {
        kafkaConsumer.subscribe(Collections.singletonList(topic));

        kafkaConsumer.poll(0);
        log.info("---------------3--------------");
        kafkaConsumer.seek(new TopicPartition(topic, partition), offset);
        log.info("---------------4--------------");
        ConsumerRecords<String, Object> records = kafkaConsumer.poll(3000L); // Duration.ofMillis(0)
        log.info("---------------5--------------");
        if(records.iterator().hasNext()) {
            ConsumerRecord<String, Object> record = records.iterator().next();
        }
    }
}
