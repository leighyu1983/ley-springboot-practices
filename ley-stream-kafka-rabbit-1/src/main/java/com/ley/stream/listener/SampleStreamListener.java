package com.ley.stream.listener;


import com.ley.stream.binding.MyInputBinding;
import com.ley.stream.entity.School;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding(MyInputBinding.class)
public class SampleStreamListener {

	@StreamListener(MyInputBinding.INPUT_R_DIRECT_Q1)
	public void inputRDirectQ1(Message<School> message) {
		log.debug("listener received....");
	}
}
