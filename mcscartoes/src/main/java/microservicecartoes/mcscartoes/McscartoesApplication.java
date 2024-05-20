package microservicecartoes.mcscartoes;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class McscartoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(McscartoesApplication.class, args);
	}

}
