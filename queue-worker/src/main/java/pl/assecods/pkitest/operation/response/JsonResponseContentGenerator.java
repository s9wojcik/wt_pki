package pl.assecods.pkitest.operation.response;

import java.util.List;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.assecods.pkitest.model.Image;
import pl.assecods.pkitest.operation.ErrorCode;

@Component
public class JsonResponseContentGenerator {
	
	public ResponseContent prepareResponseContent(String url) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		return new ResponseContent(MessageProperties.CONTENT_TYPE_JSON, mapper.writeValueAsString(url));
	}
	
	public ResponseContent prepareResponseContent(List<Image> list) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return new ResponseContent(MessageProperties.CONTENT_TYPE_JSON, mapper.writeValueAsString(list));
	}
	
	public ResponseContent prepareResponseContent(Image image) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return new ResponseContent(MessageProperties.CONTENT_TYPE_JSON, mapper.writeValueAsString(image));
	}
	
	public ResponseContent prepareResponseErrorContent(ErrorCode errorCode, String ...params) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return new ResponseContent(MessageProperties.CONTENT_TYPE_JSON, mapper.writeValueAsString(errorCode.toString() + ":" + String.join(",", params)));
	}
}
