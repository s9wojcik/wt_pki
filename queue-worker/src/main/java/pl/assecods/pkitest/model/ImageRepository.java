package pl.assecods.pkitest.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
	
	List<Image> findByUserId(String userId);
	List<Image> findByUserIdAndName(String userId, String name);
}
