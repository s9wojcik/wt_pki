package pl.assecods.pkitest.repository.db;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import pl.assecods.pkitest.model.Image;
import pl.assecods.pkitest.model.ImageRepository;

@Component
public class MetaDataRepository {
	
	@Autowired
    private ImageRepository imageRepository;
	
	
	public Image saveImage(String userId, String name, String encodedName, String url) {
		return imageRepository.save(new Image(userId, name, encodedName, url, new Date()));
	}
	
	public Image getImage(String userId, String name) {
		List<Image> images = imageRepository.findByUserIdAndName(userId, name);
		
		if (CollectionUtils.isEmpty(images)) {
			return null;
		}
		
		return images.get(0);
	}
	
	public List<Image> getImages(String userId) {
		List<Image> images = imageRepository.findByUserId(userId);
		return images;
	}
	
	public boolean isImageExists(String userId, String name) {
		if (getImage(userId, name) == null) {
			return false;
		} else {
			return true;
		}
	}
}
