package com.ley.controller;

import com.lei.component.HelloComponent;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.*;

@RestController
public class TestController {

	@Resource
	HelloComponent helloComponent;
	@RequestMapping("/hi")
	public String hello() {
		return helloComponent.say("nice");
	}

	@RequestMapping("/read-xml")
	public String readXml() throws Exception {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		org.springframework.core.io.Resource[] resources = resolver.getResources("mapper/*.xml");
		for(org.springframework.core.io.Resource r : resources) {

			// r.getFile() wrong!!!!
//			System.out.println(r.getFilename());
//			EncodedResource encRes = new EncodedResource(r, "UTF-8");
//			String content = FileCopyUtils.copyToString(encRes.getReader());
//			System.out.println(content);
			// ---------------------

			InputStream stream = r.getInputStream();
			byte[] b=new byte[2048];
			stream.read(b);
			String contnt = new String(b, 0, 2048);

			System.out.println(contnt);
		}
		return "ok";
	}
}
