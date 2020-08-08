package com.ley.stream.controller;

import com.ley.stream.binding.MyOutputBinding;
import com.ley.stream.entity.School;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
@EnableBinding(MyOutputBinding.class)
public class SampleController {
	@Autowired MyOutputBinding myOutputBinding;

	public static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

	@GetMapping("/1")
	public String a() {
		Message<School> school = MessageBuilder.withPayload(
				new School(UUID.randomUUID().toString(), "tome", new Date()))
				.setHeader(AmqpHeaders.CORRELATION_ID, UUID.randomUUID().toString())
				.setHeader("trace-id", UUID.randomUUID().toString())
				.build();

		log.debug("............current sender thread" + Thread.currentThread().getId());
		LOGGER.debug("before sending out......");
		myOutputBinding.outPutRDirectQ1().send(school);
		return "ok";
	}
}
