package com.ley.strategy;


import com.ley.interceptor.MyInterceptor;
import com.ley.strategy.mq.IMq;
import com.ley.strategy.mq.MqKafka;
import com.ley.strategy.mq.MqRabbit;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

public class MqContextStrategy {

	/**
	 * Get Mq type by header keys
	 *
	 * @return
	 */
	public IMq getMq(Message<?> message) throws Exception {
		if(message.getHeaders().containsKey(AmqpHeaders.CHANNEL) // normal case
				|| message.getHeaders().containsKey(AmqpHeaders.RAW_MESSAGE)) { // business exception
			return new MqRabbit();
		} else if (message.getHeaders().containsKey(KafkaHeaders.OFFSET)) {
			return new MqKafka();
		} else {
			throw new Exception("mq type is not supported");
		}
	}
}
