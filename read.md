##Environments and Toolset:

Used Java 11, Maven as build tool, Spring Boot 2.7.0, Intellij Idea as IDE.

## Three log Destination have been made:
A. Send to Kafka.
B. Send to RabbitMq.
C. Others send to FlatFile.

## Steps to Follow:
1. Extracted Spring Boot Project With Web,KafKa and RabbitMQ Dependencies.
2. Install Kafka, Zookeeper and RabbitMQ in your local machine.
3. Run locally Kafka, ZooKeeper and RabbitMQ servers.

4. Start Zookeeper and kafka from cmd
a. go to kafka installation directory. Then start zookeeper and kafka as mentioned in step b and c.
b. start zookeeper using-->   .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
c. start kafka using-->   .\bin\windows\kafka-server-start.bat .\config\server.properties

5. Start Springboot application or servers from Intellij locally.

6. Two restful services have been made, one for single and another for handling bulk operations.

7. Run different Unit Tests and Bulk Tests From CentralizedLoggingApplicationTests.


8. All the logs will be captured in console and write to different files(for Example: KafKa logs going to KafkaFile.txt,RabbitMQ logs going to RabbitMQFile.txt,other logs going to FlatFile.txt).
9. Installed JMeter locally and captured test for 10,100 and 1000 concurrent users and share the result.
10. All code, test cases including bulk and load test results also pushed to github.


##Note: RabbitMQ, I did not configure locally and rabbitmq configuration is not mentioned in application properties, but individual file writing has been implemented based on input destination component.