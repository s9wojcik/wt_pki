package pl.assecods.pkitest.operation.handler.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import pl.assecods.pkitest.operation.Operation;
import pl.assecods.pkitest.operation.Params;
import pl.assecods.pkitest.operation.data.GetImageRequest;
import pl.assecods.pkitest.operation.data.GetImagesRequest;
import pl.assecods.pkitest.operation.data.ImageRequest;
import pl.assecods.pkitest.operation.data.SaveImageRequest;
import pl.assecods.pkitest.operation.handler.GetImageHandler;
import pl.assecods.pkitest.operation.handler.GetImagesHandler;
import pl.assecods.pkitest.operation.handler.ImageHandler;
import pl.assecods.pkitest.operation.handler.SaveImageHandler;
import pl.assecods.pkitest.operation.handler.UnsupportedOperationHandler;

@Configuration
public class ImageHandlerFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageHandlerFactory.class);	
	
	@Bean
	@Scope("prototype")
	public ImageHandler getHandler(Message message) {
		String messageId = message.getMessageProperties().getMessageId();
		String userId = (String) message.getMessageProperties().getHeaders().get(Params.USER_ID);
		String operation = (String) message.getMessageProperties().getHeaders().get(Params.OPERATION);
		ImageRequest imageRequestData = new ImageRequest(userId, messageId, operation);
		
		if (Operation.SAVE_IMAGE == imageRequestData.getOperation()) {
			String imageName = (String) message.getMessageProperties().getHeaders().get(Params.IMAGE_NAME);
			
			SaveImageRequest saveImageRequestData = new SaveImageRequest(imageRequestData, imageName, message.getBody());
			logger.debug("{}", saveImageRequestData);
			return new SaveImageHandler(saveImageRequestData);
		} else if (Operation.GET_IMAGE == imageRequestData.getOperation()) {
			String imageName =  (String) message.getMessageProperties().getHeaders().get(Params.IMAGE_NAME);
			
			GetImageRequest getImageRequestData = new GetImageRequest(imageRequestData, imageName);
			logger.debug("{}", getImageRequestData);
			return new GetImageHandler(getImageRequestData);
		} else if (Operation.GET_IMAGES == imageRequestData.getOperation()) {
			
			GetImagesRequest getImagesRequestData = new GetImagesRequest(imageRequestData);
			logger.debug("{}", getImagesRequestData);
			return new GetImagesHandler(getImagesRequestData);
		} else {
			logger.debug("{}", imageRequestData);
			return new UnsupportedOperationHandler(imageRequestData);
		}
	}
}
