package pl.assecods.pkitest.repository.binary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BinaryRepository {
	
	@Value("${queue-worker.binary-repository.base-path}")
	private String basePath;

	
	public String save(String name, byte[] data) throws IOException {
		Path path = Paths.get(basePath + File.separator + name);
		path = Files.write(path, data);
		return path.toAbsolutePath().toString();
	}
}
