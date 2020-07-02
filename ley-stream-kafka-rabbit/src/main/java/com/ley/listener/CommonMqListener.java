package com.ley.listener;


import com.ley.entity.School;
import com.ley.sender.CustomBinding;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@Slf4j
public class CommonMqListener {

	@StreamListener(CustomBinding.INPUT_R1)
	public void recieve(Message<School> message) {
		Channel channel = (com.rabbitmq.client.Channel)message.getHeaders().get(AmqpHeaders.CHANNEL);
		Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		channel.basicAck(deliveryTag, false);
		log.debug("receiving.....{}", message.getPayload());
	}
}
