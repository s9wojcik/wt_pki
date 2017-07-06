package pl.assecods.pkitest.operation.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pl.assecods.pkitest.model.Image;
import pl.assecods.pkitest.operation.ErrorCode;
import pl.assecods.pkitest.operation.data.GetImageRequest;
import pl.assecods.pkitest.operation.response.JsonResponseContentGenerator;
import pl.assecods.pkitest.repository.db.MetaDataRepository;
import pl.assecods.pkitest.sender.WorkerResponder;

public class GetImageHandler implements ImageHandler {
	private static final Logger logger = LoggerFactory.getLogger(GetImageHandler.class);
	
	@Autowired
	private MetaDataRepository metaDataRepository;
	
	@Autowired
	private WorkerResponder workerResponder;
	
	@Autowired
	JsonResponseContentGenerator responseGenerator;
	
	private GetImageRequest getImageRequestData;
	

	public GetImageHandler(GetImageRequest getImageRequestData) {
		this.getImageRequestData = getImageRequestData;
	}

	@Override
	public void handleMessage() throws Exception {
		try {
			Image image = metaDataRepository.getImage(getImageRequestData.getImageRequestData().getUserId(), getImageRequestData.getImageName());
		
			workerResponder.sendSuccessResponse(getImageRequestData.getImageRequestData(), responseGenerator.prepareResponseContent(image));
		} catch (Exception e) {
			logger.error("{}", e);
			workerResponder.sendErrorResponse(getImageRequestData.getImageRequestData(), responseGenerator.prepareResponseErrorContent(ErrorCode.GENERAL_HANDLER_EXCEPTION));
		}
	}
}
