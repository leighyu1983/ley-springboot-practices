package com.ley.sender;


import com.ley.configuration.MyKafkaProducerK1Binding;
import com.ley.configuration.MyRabbitProducerRBinding;
import com.ley.entity.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;


/**
 * 这个注解Source是Stream提供的，点进去看源码，可以看到output和input,这和配置文件中的output，input对应的。
 *
 * input -> current -> output
 *
 */
@EnableBinding({MyRabbitProducerRBinding.class, MyKafkaProducerK1Binding.class})
public class MySender {
	//@Autowired private Source source;
	@Autowired private MyRabbitProducerRBinding rSource;
	@Autowired private MyKafkaProducerK1Binding kSource;


	public void sendR1Msg(School school){
		Message<School> message = MessageBuilder.withPayload(school).build();
		boolean r = rSource.outputR1().send(message);
		System.out.println(r);
	}

	public void sendR2Msg(List<School> schools){
		Message<List<School>> message = MessageBuilder.withPayload(schools).build();
		boolean r = rSource.outputR2().send(message);
		System.out.println(r);
	}

	public void sendR3Msg(School school){
		Message<School> message = MessageBuilder.withPayload(school).build();
		boolean r = rSource.outputR3().send(message);
		System.out.println(r);
	}

	public void sendR4Msg(School school){
		Message<School> message = MessageBuilder.withPayload(school).build();
		boolean r = rSource.outputR4().send(message);
		System.out.println(r);
	}

	public void sendK1Msg(School school){
		Message<School> message = MessageBuilder.withPayload(school).build();
		boolean r = kSource.outputK1().send(message);
		System.out.println(r);
	}
}


