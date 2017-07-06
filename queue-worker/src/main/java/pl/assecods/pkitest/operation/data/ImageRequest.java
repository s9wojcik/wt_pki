package pl.assecods.pkitest.operation.data;

import pl.assecods.pkitest.operation.Operation;

public class ImageRequest {
	
	private String userId;
	
	private String messageId;
	
	private Operation operation;
	
	
	public ImageRequest(String userId, String messageId, String operation) {
		this.userId = userId;
		this.messageId = messageId;
		this.operation = Operation.valueOf(operation);
	}

	public String getUserId() {
		return userId;
	}

	public String getMessageId() {
		return messageId;
	}

	public Operation getOperation() {
		return operation;
	}

	@Override
	public String toString() {
		return "ImageRequest [userId=" + userId + ", messageId=" + messageId + ", operation=" + operation + "]";
	}
}
