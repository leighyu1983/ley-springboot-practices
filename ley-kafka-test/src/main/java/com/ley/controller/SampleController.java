package com.ley.controller;

import com.ley.pojo.MyBean2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
public class SampleController {

    // local
    private ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>(1000);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired(required = false)
    private MyBean2 myBean2;

    @GetMapping("/hello")
    public void hello() throws IOException {
        Resource[]  resources = new PathMatchingResourcePatternResolver().getResources("classpath*:com/ley/**/*.class");
        MetadataReader reader = new CachingMetadataReaderFactory().getMetadataReader(resources[0]);
        boolean r = reader.getAnnotationMetadata().hasMetaAnnotation("org.springframework.stereotype.Component");
        myBean2.test();
    }

    @GetMapping("/send/{topic}/{message}")
    public String send(@PathVariable String topic, @PathVariable String message) {
        log.debug(" debug.........send to kafka topic {}, message {}", topic, message);
        log.info(" info.........send to kafka topic {}, message {}", topic, message);
        ListenableFuture<SendResult<String, Object>> resultFuture = kafkaTemplate.send(topic, message);

        resultFuture.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.debug("msg OK. " + result.toString());
                log.info("msg OK. " + result.toString());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("msg send failed.", ex);
            }
        });
        return "ok";
    }
}
