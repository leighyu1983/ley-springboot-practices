package com.ley.listener;

import com.ley.configuration.MyKafkaConsumerK1Binding;
import com.ley.stream.entity.School;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@Slf4j
@EnableBinding(MyKafkaConsumerK1Binding.class)
public class CommonKafkaListener {

	@StreamListener(MyKafkaConsumerK1Binding.CONSUMER_K1)
	public void recieveK1(Message<School> message) {
		throw new RuntimeException("..........this is runtime exception..............");

		//System.out.print("CONSUMER_K1..." + message.getPayload());
//		Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
//		if (acknowledgment != null) {
//			System.out.println("Acknowledgment provided");
//			acknowledgment.acknowledge();
//		}

	}
}
