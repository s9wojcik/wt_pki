package pl.assecods.pkitest.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.assecods.pkitest.operation.handler.ImageHandler;
import pl.assecods.pkitest.operation.handler.factory.ImageHandlerFactory;

@Component
public class RestQueueListener implements MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(RestQueueListener.class);	
	
	@Autowired
	ImageHandlerFactory handlerFactory;
	
	
	@Override
	@RabbitListener(bindings = @QueueBinding(
		value = @Queue(value = "${queue-worker.rest-queue-name}", durable = "${queue-worker.rest-queue-durable}"),
		exchange = @Exchange(value = "${queue-worker.rest-exchange-name}", durable = "${queue-worker.rest-exchange-durable}"), key = "${queue-worker.rest-queue-name}"))
	public void onMessage(Message message) {
		try {
			ImageHandler handler = handlerFactory.getHandler(message);
			handler.handleMessage();
		} catch (Exception e) {
			logger.error("{}", e);
		}
	}
}
