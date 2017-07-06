package pl.assecods.pkitest.operation.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pl.assecods.pkitest.operation.ErrorCode;
import pl.assecods.pkitest.operation.data.SaveImageRequest;
import pl.assecods.pkitest.operation.data.Utils;
import pl.assecods.pkitest.operation.response.JsonResponseContentGenerator;
import pl.assecods.pkitest.repository.binary.BinaryRepository;
import pl.assecods.pkitest.repository.db.MetaDataRepository;
import pl.assecods.pkitest.sender.WorkerResponder;

public class SaveImageHandler implements ImageHandler {
	private static final Logger logger = LoggerFactory.getLogger(SaveImageHandler.class);
	
	@Autowired
	private MetaDataRepository metaDataRepository;
	
	@Autowired
	private BinaryRepository binaryRepository;
	
	@Autowired
	private WorkerResponder workerResponder;
	
	@Autowired
	JsonResponseContentGenerator responseGenerator;
	
	@Autowired
	Utils utils;
	
	private SaveImageRequest saveImageRequestData;
	
	
	public SaveImageHandler(SaveImageRequest saveImageRequestData) {
		this.saveImageRequestData = saveImageRequestData;
	}

	@Override
	public void handleMessage() throws Exception {
		try {
			String imageName = saveImageRequestData.getImageName();
			
			if (metaDataRepository.isImageExists(saveImageRequestData.getImageRequest().getUserId(), imageName)) {
				workerResponder.sendErrorResponse(saveImageRequestData.getImageRequest(), responseGenerator.prepareResponseErrorContent(ErrorCode.IMAGE_ALREADY_EXISTS, imageName));
			} else {
				String encodedImageName = utils.generateEncodedName(saveImageRequestData.getImageRequest(), imageName);
				String url = binaryRepository.save(encodedImageName, saveImageRequestData.getImageContent());
				
				metaDataRepository.saveImage(saveImageRequestData.getImageRequest().getUserId(), imageName, encodedImageName, url);
				
				workerResponder.sendSuccessResponse(saveImageRequestData.getImageRequest(), responseGenerator.prepareResponseContent(url));
			}
		} catch (Exception e) {
			logger.error("{}", e);
			workerResponder.sendErrorResponse(saveImageRequestData.getImageRequest(), responseGenerator.prepareResponseErrorContent(ErrorCode.GENERAL_HANDLER_EXCEPTION));
		}
	}
}
