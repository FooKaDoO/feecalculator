package com.feecalculator.app;

import com.feecalculator.app.service.DatafetchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	DatafetchService datafetchService;
	@Test
	void testDataFetch() {
		
	}

}
