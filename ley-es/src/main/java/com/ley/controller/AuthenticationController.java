package com.ley.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ley.pojo.Article;
import lombok.SneakyThrows;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;


@RestController
public class AuthenticationController {
	@Autowired private RestHighLevelClient restHighLevelClient;

	@GetMapping("/1")
	public String getIndex() throws IOException {

		//CreateIndexRequest request = new CreateIndexRequest("jing");
		IndexRequest request = new IndexRequest("jing").source(getArticleJson(), XContentType.JSON);
//		request.settings(Settings.builder()
//				.put("index.number_of_shards",3)
//				.put("index.number_of_replicas",2));
//		request.mapping(getArticleJson(), XContentType.JSON);

		IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
		return "";
	}

	@SneakyThrows
	private String getArticleJson() {
		Article article = new Article();
		article.setCategory(1);
		article.setCdate(new Date());
		article.setContent("这是中文教程，欢迎使用该文档");
		article.setName("测试用例");

		return new ObjectMapper().writeValueAsString(article);
	}

}
