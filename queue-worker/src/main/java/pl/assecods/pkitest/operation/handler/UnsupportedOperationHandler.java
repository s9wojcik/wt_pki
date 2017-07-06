package pl.assecods.pkitest.operation.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.assecods.pkitest.operation.data.ImageRequest;

public class UnsupportedOperationHandler implements ImageHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(UnsupportedOperationHandler.class);	
	
	private ImageRequest imageRequestData;
	
	
	public UnsupportedOperationHandler (ImageRequest imageRequestData) {
		this.imageRequestData = imageRequestData;
	}

	@Override
	public void handleMessage() {
		logger.warn("The opperation is not supported.", imageRequestData);
	}
}
