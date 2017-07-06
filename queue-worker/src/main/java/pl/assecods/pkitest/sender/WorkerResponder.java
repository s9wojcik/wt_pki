package pl.assecods.pkitest.sender;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import pl.assecods.pkitest.operation.Params;
import pl.assecods.pkitest.operation.ResponseOperation;
import pl.assecods.pkitest.operation.Status;
import pl.assecods.pkitest.operation.data.ImageRequest;
import pl.assecods.pkitest.operation.response.ResponseContent;

@Component
public class WorkerResponder {
	@Value("${queue-worker.worker-exchange-name}")
	public String workerExchangeName;
	
	@Value("${queue-worker.worker-routing-key}")
	public String workerRoutingKey;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	
	public void sendSuccessResponse(ImageRequest imageRequestData, ResponseContent responseContent) {
		sendResponse(imageRequestData, Status.SUCCESS, responseContent);
	}
	
	public void sendErrorResponse(ImageRequest imageRequestData, ResponseContent responseContent) {
		sendResponse(imageRequestData, Status.ERROR, responseContent);
	}
	
	public void sendResponse(ImageRequest imageRequestData, Status status, ResponseContent responseContent) {
	  	Message responseMessage = MessageBuilder.withBody(responseContent.getContent().getBytes())
	    		.setContentType(responseContent.getContentType())
	    		.setMessageId(imageRequestData.getMessageId())
	    		.setHeader(Params.USER_ID, imageRequestData.getUserId())
	    		.setHeader(Params.REPLY_TO_OPERATION, imageRequestData.getOperation())
	    		.setHeader(Params.OPERATION, ResponseOperation.STATUS)
	    		.setHeader(Params.STATUS, status)
	    		.build();
	    	
	    rabbitTemplate.send(workerExchangeName, workerRoutingKey, responseMessage);
	}
}
