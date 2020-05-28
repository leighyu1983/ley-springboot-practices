package com.ley.scenario.serialization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class SerializeObjWithJackson {
	@JsonProperty("ni-hao")
	private String niHao;
}
