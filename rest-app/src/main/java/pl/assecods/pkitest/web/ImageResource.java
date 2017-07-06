package pl.assecods.pkitest.web;

import java.io.IOException;
import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.assecods.pkitest.entity.User;
import pl.assecods.pkitest.repository.UserRepository;
import pl.assecods.pkitest.security.SecurityUtils;

@Controller
public class ImageResource {
	private static final String UPLOAD_FORM = "/uploadForm";
	private static final String UPLOAD_FORM_URL = "/upload-form";
	private static final String UPLOAD_IMAGE_URL = "/upload-image";
	private static final String ERROR_FORM = "/errorForm";
	private static final String REDIRECT = "redirect:";
	
	private static final String SAVE_IMAGE_OPERATION = "SAVE_IMAGE";
	
    @Autowired
    private UserRepository userRepository;
	
	@Autowired
	private TopicExchange publisherExchange;
    
	@Autowired
	private Queue publisherQueue;

	@Autowired
	private ConnectionFactory publisherConnectionFactory;
	
	@GetMapping(UPLOAD_FORM_URL)
	public String uploadForm(Model model) throws IOException {
		User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
		model.addAttribute("userMessage", "Hello " + user.getFirstName() + " " + user.getLastName() + "!");
		return UPLOAD_FORM;
	}

	@PostMapping(UPLOAD_IMAGE_URL)
	public String uploadImage(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			String uuid = UUID.randomUUID().toString();
			String userLogin = SecurityUtils.getCurrentUserLogin();

			Message messageSaveImage = MessageBuilder.withBody(file.getBytes())
					.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
					.setMessageId(uuid)
					.setHeader("userId", userLogin)
					.setHeader("operation", SAVE_IMAGE_OPERATION)
					.setHeader("imageName", file.getOriginalFilename())
					.build();

			sendMessage(publisherExchange.getName(), publisherQueue.getName(), messageSaveImage);

			redirectAttributes.addFlashAttribute("uploadMessage", "FILE UPLOADED: " + file.getOriginalFilename() + "!");
			
			System.out.println("Uuid: " + uuid + " userLogin: " + userLogin + " file: " + file.getOriginalFilename());
		} catch (Exception e) {
			return ERROR_FORM;	
		}

		return REDIRECT + UPLOAD_FORM_URL;
	}

	private void sendMessage(String exchangeName, String queueName, Message message) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(publisherConnectionFactory);
		rabbitTemplate.send(exchangeName, queueName, message);
	}
}
