package pl.assecods.pkitest.operation.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pl.assecods.pkitest.model.Image;
import pl.assecods.pkitest.operation.ErrorCode;
import pl.assecods.pkitest.operation.data.GetImagesRequest;
import pl.assecods.pkitest.operation.response.JsonResponseContentGenerator;
import pl.assecods.pkitest.repository.db.MetaDataRepository;
import pl.assecods.pkitest.sender.WorkerResponder;

public class GetImagesHandler implements ImageHandler {
	private static final Logger logger = LoggerFactory.getLogger(GetImagesHandler.class);
	
	@Autowired
	private MetaDataRepository metaDataRepository;
	
	@Autowired
	private WorkerResponder workerResponder;
	
	@Autowired
	JsonResponseContentGenerator responseGenerator;
	
	private GetImagesRequest getImagesRequestData;
	

	public GetImagesHandler(GetImagesRequest getImagesRequestData) {
		this.getImagesRequestData = getImagesRequestData;
	}

	@Override
	public void handleMessage() throws Exception {
		try {
			List<Image> images = metaDataRepository.getImages(getImagesRequestData.getImageRequest().getUserId());
		
			workerResponder.sendSuccessResponse(getImagesRequestData.getImageRequest(), responseGenerator.prepareResponseContent(images));
		} catch (Exception e) {
			logger.error("{}", e);
			workerResponder.sendErrorResponse(getImagesRequestData.getImageRequest(), responseGenerator.prepareResponseErrorContent(ErrorCode.GENERAL_HANDLER_EXCEPTION));
		}
	}
}
