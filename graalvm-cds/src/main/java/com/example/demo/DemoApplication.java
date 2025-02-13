package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RestController
	@RequestMapping
	public class MyController{

		private static final Logger log = LoggerFactory.getLogger(MyController.class);


		@RequestMapping("/")
		public String hello(){
			log.info("Stepping into hello world");
			return "Hello World";
		}

	}

}
