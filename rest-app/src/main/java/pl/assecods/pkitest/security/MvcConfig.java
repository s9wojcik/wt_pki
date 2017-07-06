package pl.assecods.pkitest.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("redirect:/upload-form");
        registry.addViewController("/").setViewName("redirect:/upload-form");
        registry.addRedirectViewController("/home", "/upload-form");
        registry.addViewController("/upload-form").setViewName("/upload-form");
        registry.addViewController("/upload-image").setViewName("/upload-image");
        registry.addViewController("/login").setViewName("login");
    }
}
