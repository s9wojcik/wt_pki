package pl.assecods.pkitest.operation.response;

public class ResponseContent {
	
	private String contentType;
	
	private String content;
	
	public ResponseContent(String contentType, String content) {
		this.contentType = contentType;
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public String getContent() {
		return content;
	}
}
