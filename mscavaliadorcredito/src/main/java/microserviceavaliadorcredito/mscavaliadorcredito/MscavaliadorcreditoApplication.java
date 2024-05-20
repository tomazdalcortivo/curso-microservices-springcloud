package microserviceavaliadorcredito.mscavaliadorcredito;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableRabbit
public class MscavaliadorcreditoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscavaliadorcreditoApplication.class, args);
	}

}
