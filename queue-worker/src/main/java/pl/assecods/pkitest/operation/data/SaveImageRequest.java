package pl.assecods.pkitest.operation.data;

public class SaveImageRequest {

	private ImageRequest imageRequest;
	
	private String imageName;
	
	private byte[] imageContent;
	
	
	public SaveImageRequest(ImageRequest imageRequest, String imageName, byte[] imageContent) {
		this.imageRequest = imageRequest;
		this.imageName = imageName;
		this.imageContent = imageContent;
	}

	public ImageRequest getImageRequest() {
		return imageRequest;
	}

	public String getImageName() {
		return imageName;
	}

	public byte[] getImageContent() {
		return imageContent;
	}

	@Override
	public String toString() {
		return "SaveImageRequest [imageRequest=" + imageRequest + ", imageName=" + imageName + "]";
	}
}
