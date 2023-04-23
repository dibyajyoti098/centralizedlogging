package com.log.centralizedlogging;


import com.log.centralizedlogging.model.LogRequest;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@SpringBootTest
class CentralizedLoggingApplicationTests {


	@Test
	void contextLoads() {
	}

	@Test
	 void sendRequestToKafKa(){

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		LogRequest request = new LogRequest();
		request.setApplicationId("Kafka");
		request.setTraceId("KafkaTr_"+ UUID.randomUUID().toString());
		request.setComponentName("Kafka");
		request.setRequestId("Kafka_req".hashCode());
		request.setSeverity("3");
		request.setTimestamp(new Date());
		request.setMessage("Sending to Kafka");
		HttpEntity<LogRequest> entity = new HttpEntity<>(request, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/log", HttpMethod.POST, entity, String.class);
		assert response.getStatusCodeValue()==200;
	}


	@Test
	void sendBulkRequestToKafKa(){

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<LogRequest> logRequests = new ArrayList<>();
		LogRequest request=new LogRequest();
		for(int i=0;i<10;i++) {
			request.setApplicationId("KafkaBulk");
			request.setTraceId("KafkaTr_"+ UUID.randomUUID().toString());
			request.setComponentName("Kafka");
			request.setRequestId("Kafka_reqBulk".hashCode());
			request.setSeverity("3");
			request.setTimestamp(new Date());
			request.setMessage("Sending to Kafka in bulk");
			logRequests.add(request);
		}
		HttpEntity<List<LogRequest>> entity = new HttpEntity<>(logRequests, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/log/bulk", HttpMethod.POST, entity, String.class);
		assert response.getStatusCodeValue()==200;
	}

	@Test
	void sendRequestToFlatFile(){

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		LogRequest request = new LogRequest();
		request.setApplicationId("Flat");
		request.setTraceId("FlatTr_"+ UUID.randomUUID().toString());
		request.setComponentName("FlatFile");
		request.setRequestId("Flat_req".hashCode());
		request.setSeverity("3");
		request.setTimestamp(new Date());
		request.setMessage("Sending to FlatFile");
		HttpEntity<LogRequest> entity = new HttpEntity<>(request, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/log", HttpMethod.POST, entity, String.class);
		System.out.println(response);
	}

	@Test
	void sendBulkRequestToFlatFile(){

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<LogRequest> logRequests = new ArrayList<>();
		LogRequest request=new LogRequest();
		for(int i=0;i<10;i++) {
			request.setApplicationId("FlatBulk");
			request.setTraceId("FlatTr_"+ UUID.randomUUID().toString());
			request.setComponentName("FlatFile");
			request.setRequestId("Flat_reqBulk".hashCode());
			request.setSeverity("3");
			request.setTimestamp(new Date());
			request.setMessage("Sending to FlatFile in bulk");
			logRequests.add(request);
		}
		HttpEntity<List<LogRequest>> entity = new HttpEntity<>(logRequests, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/log/bulk", HttpMethod.POST, entity, String.class);
		assert response.getStatusCodeValue()==200;
	}

	@Test
	void sendRequestToRabbitMQ(){

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		LogRequest request = new LogRequest();
		request.setApplicationId("RabbitMQ");
		request.setTraceId("RabbitMQTr_"+ UUID.randomUUID().toString());
		request.setComponentName("RabbitMQ");
		request.setRequestId("RabbitMQ_req".hashCode());
		request.setSeverity("3");
		request.setTimestamp(new Date());
		request.setMessage("Sending to RabbitMQ");
		HttpEntity<LogRequest> entity = new HttpEntity<>(request, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/log", HttpMethod.POST, entity, String.class);
		System.out.println(response);	}

	@Test
	void sendBulkRequestToRabbitMQ(){

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<LogRequest> logRequests = new ArrayList<>();
		LogRequest request=new LogRequest();
		for(int i=0;i<10;i++) {
			request.setApplicationId("RabbitMQBulk");
			request.setTraceId("RabbitMQTr_"+ UUID.randomUUID().toString());
			request.setComponentName("RabbitMQ");
			request.setRequestId("RabbitMQ_reqBulk".hashCode());
			request.setSeverity("3");
			request.setTimestamp(new Date());
			request.setMessage("Sending to RabbitMQ in bulk");
		}
		HttpEntity<List<LogRequest>> entity = new HttpEntity<>(logRequests, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/log/bulk", HttpMethod.POST, entity, String.class);
		assert response.getStatusCodeValue()==200;
	}
}
