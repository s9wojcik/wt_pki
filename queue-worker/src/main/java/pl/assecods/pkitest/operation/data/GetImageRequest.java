package pl.assecods.pkitest.operation.data;

public class GetImageRequest {
	
	private ImageRequest imageRequest;
	
	private String imageName;
	
	public GetImageRequest(ImageRequest imageRequest, String imageName) {
		this.imageRequest = imageRequest;
		this.imageName = imageName;
	}

	public ImageRequest getImageRequestData() {
		return imageRequest;
	}

	public String getImageName() {
		return imageName;
	}

	@Override
	public String toString() {
		return "GetImageRequest [imageRequest=" + imageRequest + ", imageName=" + imageName + "]";
	}
}
