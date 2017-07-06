package pl.assecods.pkitest.operation.data;

public class GetImagesRequest {
	
	private ImageRequest imageRequest;
	
	public GetImagesRequest(ImageRequest imageRequest) {
		this.imageRequest = imageRequest;
	}

	public ImageRequest getImageRequest() {
		return imageRequest;
	}

	@Override
	public String toString() {
		return "GetImagesRequest [imageRequest=" + imageRequest + "]";
	}
}
