package com.example.demo;

import org.bson.Document;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	MongoTemplate mongoTemplate;

	@Test
	void contextLoads() {
		Document document = new Document();
		Document inner = new Document();
		LocalDate date = LocalDate.of(2001, 1, 1);
		document.put("fieldA", date);
		inner.put("fieldB", date);
		document.put("inner", inner);
		mongoTemplate.insert(document, "someCollection");

		List<Document> matched = mongoTemplate.findAll(Document.class, "someCollection");
		Document result = matched.get(0);
		assertThat(result.get("inner", Document.class).get("fieldB"),
				Matchers.equalTo(result.get("fieldA")));
		System.out.println(result.toJson());
	}

}
