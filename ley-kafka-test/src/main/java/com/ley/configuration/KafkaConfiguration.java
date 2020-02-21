package com.ley.configuration;


import com.ley.configurationProperty.MyKafkaProperty;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;


import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfiguration {

    @Autowired
    private MyKafkaProperty myKafkaProperty;


    @Bean
    public Map<String, Object> producerConfigs() throws Exception {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.ACKS_CONFIG, "0");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, myKafkaProperty.getBootstrapServers());
        props.put(ProducerConfig.RETRIES_CONFIG, myKafkaProperty.getRetries());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, myKafkaProperty.getBatchSize());
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, myKafkaProperty.getBufferMemory());
        //props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Class.forName(myKafkaProperty.getProducerrKeySerializer()));
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Class.forName(myKafkaProperty.getProducerValueSerializer()));
        return props;
    }
    /**
     *  生产者工厂
     */
    @Bean
    public ProducerFactory<String, Object> producerFactory() throws Exception {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     *  生产者模板
     */
    @Bean(name = "kafkaTemplate")
    public KafkaTemplate<String, Object> kafkaTemplate() throws Exception {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     *  消费者配置信息
     */
    @Bean
    public Map<String, Object> consumerConfigs() throws Exception {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, myKafkaProperty.getGroupId());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, myKafkaProperty.getAutoOffsetReset());
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, myKafkaProperty.getBootstrapServers());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, myKafkaProperty.getMaxPollRecords());
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, myKafkaProperty.getMaxPollIntervalMs());
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 120000);
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 180000);
        //props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Class.forName(myKafkaProperty.getConsumerKeyDeserializer()));
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Class.forName(myKafkaProperty.getConsumerValueDeserializer()));
        return props;
    }

    /**
     *  消费者批量工程
     */
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() throws Exception {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    /**
     *  多线程并发处理 KafkaMessageListenerContainer
     *  @KafkaListener(topics = {"first_top"}, containerFactory="kafkaListenerContainerFactory")
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() throws Exception {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(myKafkaProperty.getConcurrency());
        //factory.setBatchListener(true);
        factory.getContainerProperties().setPollTimeout(myKafkaProperty.getPollTimeout());
        return factory;
    }
}
