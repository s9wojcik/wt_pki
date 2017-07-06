package pl.assecods.pkitest;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import pl.assecods.pkitest.operation.Operation;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueueWorkerApplicationTests {
	
	@Value("${queue-worker.rest-exchange-name}")
	private String restExchange;
	
	@Value("${queue-worker.rest-routing-key}")
	private String restRoutingKey;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	
	@Test
	public void test() throws IOException {
		//sendImagesToQueue();
		getImages();
	}
	
	public void sendImagesToQueue() throws IOException {
    	Message messageSaveImage1 = MessageBuilder.withBody(IOUtils.toByteArray(new ClassPathResource("Desert.jpg").getInputStream()))
    			.setContentType(MessageProperties.CONTENT_TYPE_BYTES)
    		    .setMessageId(UUID.randomUUID().toString())
    		    .setHeader("userId", "user1")
    		    .setHeader("operation", Operation.SAVE_IMAGE)
    		    .setHeader("imageName", "Desert.jpg")
    		    .build();
    	
    	Message messageSaveImage2 = MessageBuilder.withBody(IOUtils.toByteArray(new ClassPathResource("Koala.jpg").getInputStream()))
    			.setContentType(MessageProperties.CONTENT_TYPE_BYTES)
    		    .setMessageId(UUID.randomUUID().toString())
    		    .setHeader("userId", "user1")
    		    .setHeader("operation", Operation.SAVE_IMAGE)
    		    .setHeader("imageName", "Koala.jpg")
    		    .build();
    	
    	rabbitTemplate.send(restExchange, restRoutingKey, messageSaveImage1);
    	rabbitTemplate.send(restExchange, restRoutingKey, messageSaveImage2);
	}
	
	public void getImages() throws IOException {
		Message messageGetImage = MessageBuilder.withBody("".getBytes())
    		    .setMessageId(UUID.randomUUID().toString())
    		    .setHeader("userId", "user1")
    		    .setHeader("operation", Operation.GET_IMAGE)
    		    .setHeader("imageName", "Desert.jpg")
    		    .build();
    	
    	Message messageGetImages = MessageBuilder.withBody("".getBytes())
    		    .setMessageId(UUID.randomUUID().toString())
    		    .setHeader("userId", "user1")
    		    .setHeader("operation", Operation.GET_IMAGES)
    		    .build();
    	
    	rabbitTemplate.send(restExchange, restRoutingKey, messageGetImage);
    	rabbitTemplate.send(restExchange, restRoutingKey, messageGetImages);
	}
}
