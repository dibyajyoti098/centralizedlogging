package com.log.centralizedlogging.controller;

import com.log.centralizedlogging.model.LogRequest;
import com.log.centralizedlogging.service.FileWriterService;
import com.log.centralizedlogging.service.JsonKafkaProducer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
public class LoggingController {


    private static final Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @Autowired
    private JsonKafkaProducer jsonKafkaProducer;

    @Autowired
    private FileWriterService fileWriterService;


    @PostMapping("/log")
    public ResponseEntity<String> logMessage(@RequestBody LogRequest logRequest) throws IOException {
        List<LogRequest> logRequests=new ArrayList<>();
        logRequests.add(logRequest);
        logRequestHandler(logRequests);
        return new ResponseEntity<>("Log message processed successfully ", HttpStatus.OK);
    }


    @PostMapping("/log/bulk")
    public ResponseEntity<String> logMessage(@RequestBody List<LogRequest> logRequests) throws IOException {
        logRequestHandler(logRequests);
        return new ResponseEntity<>("Bulk Log message processed successfully ", HttpStatus.OK);
    }

    private void sendMessageToKafka(BufferedWriter fWriter,LogRequest request) {
        jsonKafkaProducer.sendMessage(request);
        fileWriterService.sendLogToFile(fWriter,request);
    }

    private void sendMessageToRabbitMq(BufferedWriter fWriter, LogRequest request) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        String logMessage = "RabbitMq" + " - " + request.getMessage();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("rabbitmq-logmessages", false, false, false, null);
            channel.basicPublish("", "rabbitmq-logmessages", null, logMessage.getBytes("UTF-8"));

        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
        fileWriterService.sendLogToFile(fWriter,request);
    }

    private void sendMessageToFlatFile(BufferedWriter fWriter,LogRequest request) {
        fileWriterService.sendLogToFile(fWriter,request);
    }

    private void logRequestHandler(List<LogRequest> logRequests) throws IOException {

        BufferedWriter fWriter1 = new BufferedWriter(new FileWriter(new File("KafKaFile.txt"),true));

        BufferedWriter fWriter2 = new BufferedWriter(new FileWriter(new File("RabbitMQFile.txt"),true));

        BufferedWriter fWriter3 = new BufferedWriter(new FileWriter(new File("FlatFile.txt"),true));

        for(LogRequest logRequest:logRequests) {

            if (logRequest.getApplicationId().startsWith(logRequest.getComponentName()) && logRequest.getComponentName().equalsIgnoreCase("Kafka")) {

                logger.info("addKafkaLogDetails--->" + "ApplicationID->" + logRequest.getApplicationId() + ", " + "TraceID->" + logRequest.getTraceId() + ", " + "componentName->" + logRequest.getComponentName() + "," + "RequestID->" + logRequest.getRequestId() + ", " + "Severity->" + logRequest.getSeverity() + ", " + "TimeStamp->" + logRequest.getTimestamp() + ", " + "Message->" + logRequest.getMessage());

                sendMessageToKafka(fWriter1, logRequest);
            } else if (logRequest.getApplicationId().startsWith(logRequest.getComponentName()) && logRequest.getComponentName().equalsIgnoreCase("RabbitMq")) {

                logger.info("addRabbitMqLogDetails--->" + "ApplicationID->" + logRequest.getApplicationId() + ", " + "TraceID->" + logRequest.getTraceId() + ", " + "componentName->" + logRequest.getComponentName() + "," + "RequestID->" + logRequest.getRequestId() + ",  " + "Severity->" + logRequest.getSeverity() + ", " + "TimeStamp->" + logRequest.getTimestamp() + ", " + "Message->" + logRequest.getMessage());

                sendMessageToRabbitMq(fWriter2, logRequest);

            } else {

                logger.info("addOtherLogDetails--->" + "ApplicationID->" + logRequest.getApplicationId() + ", " + "TraceID->" + logRequest.getTraceId() + "," + "componentName->" + logRequest.getComponentName() + "," + "RequestID->" + logRequest.getRequestId() + ",  " + "Severity->" + logRequest.getSeverity() + ", " + "TimeStamp->" + logRequest.getTimestamp() + ", " + "Message->" + logRequest.getMessage());

                sendMessageToFlatFile(fWriter3, logRequest);

            }
        }
        fWriter1.close();
        fWriter2.close();
        fWriter3.close();

    }


}
