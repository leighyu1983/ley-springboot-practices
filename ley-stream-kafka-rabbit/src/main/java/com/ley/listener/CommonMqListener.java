package com.ley.listener;


import com.ley.configuration.MyRabbitConsumerR1Binding;
import com.ley.stream.entity.School;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

import java.util.List;

@Slf4j
//@EnableBinding({MyRabbitConsumerR1Binding.class})
public class CommonMqListener {

	@StreamListener(MyRabbitConsumerR1Binding.consumerRA)
	public void recieveR1(Message<School> message) throws Exception {
		throw new Exception("test..................");
//		Channel channel = (com.rabbitmq.client.Channel)message.getHeaders().get(AmqpHeaders.CHANNEL);
//		Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
//		channel.basicAck(deliveryTag, false);
//		log.debug("receiving.....{}", message.getPayload());
	}

	@StreamListener(MyRabbitConsumerR1Binding.consumerRA2)
	public void recieveR2(Message<List<School>> message) throws Exception {
		//Channel channel = (com.rabbitmq.client.Channel)message.getHeaders().get(AmqpHeaders.CHANNEL);
		//Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		//channel.basicAck(deliveryTag, false);
		for(School school : message.getPayload()) {
			System.out.println("consumerRA2 receiving....." + school);
		}
	}

	@StreamListener(MyRabbitConsumerR1Binding.direct_R_consumer_A)
	public void recieveR3(Message<School> message) throws Exception {
		//Channel channel = (com.rabbitmq.client.Channel)message.getHeaders().get(AmqpHeaders.CHANNEL);
		//Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		//channel.basicAck(deliveryTag, false);
		School school = message.getPayload();
		System.out.println("direct_R_consumer_A receiving....." + school);
	}

	@StreamListener(MyRabbitConsumerR1Binding.fanout_R_consumer_cA1)
	public void recieveR4(Message<School> message) throws Exception {
		//Channel channel = (com.rabbitmq.client.Channel)message.getHeaders().get(AmqpHeaders.CHANNEL);
		//Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		//channel.basicAck(deliveryTag, false);
		School school = message.getPayload();
		System.out.println("receiving..... fanout 1...." + school);
	}

	@StreamListener(MyRabbitConsumerR1Binding.fanout_R_consumer_cA2)
	public void recieveR5(Message<School> message) throws Exception {
//		Channel channel = (com.rabbitmq.client.Channel)message.getHeaders().get(AmqpHeaders.CHANNEL);
//		Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
//		channel.basicAck(deliveryTag, false);
//		School school = message.getPayload();
//		System.out.println("receiving..... fanout 2...." + school);
	}

}
