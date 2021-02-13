package az.maqa.spring.aspect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringAopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAopApplication.class, args);
	}

}
