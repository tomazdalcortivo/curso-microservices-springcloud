package microserviceavaliadorcredito.mscavaliadorcredito.config;


import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Value("${mq.queues.emissao-cartoes: emissao-cartoes}")
    private String emissoesCartoesFila;

    @Bean
    public Queue queueEmissaoCartoes() {
        return new Queue(emissoesCartoesFila, true);
    }
}
