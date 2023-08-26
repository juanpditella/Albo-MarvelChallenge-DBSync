package ar.com.tdm.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource("file:/app/mock/config.properties")
public class Application  extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
