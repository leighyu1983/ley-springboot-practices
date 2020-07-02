package com.ley.sender;


import com.ley.entity.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * 这个注解Source是Stream提供的，点进去看源码，可以看到output和input,这和配置文件中的output，input对应的。
 */
@EnableBinding({CustomBinding.class})
public class MySender {
	//@Autowired private Source source;
	@Autowired private CustomBinding source;

	public void sendMsg(String msg){
		Message<String> message = MessageBuilder.withPayload(msg).build();
		source.outputR1().send(message);
	}

	public void sendMsg(School school){
		Message<School> message = MessageBuilder.withPayload(school).build();
		source.outputR1().send(message);
	}
}
