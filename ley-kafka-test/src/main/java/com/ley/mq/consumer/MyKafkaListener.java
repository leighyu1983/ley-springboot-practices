package com.ley.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ley.filter.MyKafkaFilter;
import com.ley.pojo.BeanNoGet;
import example.avro.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@MyKafkaFilter
public class MyKafkaListener {

    @KafkaListener(topics = "${topicName.topic1}")
    public void listenerAvro(ConsumerRecord<String, Object> record) throws Exception {

        User user = (User)record.value();

        log.info("info....record: {}", record);
        log.debug("info....record: {}", record);
        //throw new Exception("waho exception yeah....");
    }

//    @KafkaListener(topics = "${topicName.topic1}")
//    public void listenerString(ConsumerRecord<String, Object> record) throws Exception {
//
//        BeanNoGet get = new BeanNoGet("tom", 1);
//
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonValue = mapper.writeValueAsString(get);
//        BeanNoGet user = mapper.readValue(jsonValue, BeanNoGet.class);
//
//        log.info("info....record: {}", record);
//        log.debug("info....record: {}", record);
//    }
}
