package pl.assecods.pkitest.operation.data;

import org.springframework.stereotype.Component;

@Component
public class Utils {
	
	public String generateEncodedName(ImageRequest imageRequestData, String name) {
		return imageRequestData.getMessageId() + "_" + name;
	}
}
