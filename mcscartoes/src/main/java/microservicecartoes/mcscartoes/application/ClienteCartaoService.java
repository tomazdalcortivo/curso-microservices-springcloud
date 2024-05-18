package microservicecartoes.mcscartoes.application;

import lombok.RequiredArgsConstructor;
import microservicecartoes.mcscartoes.domain.ClienteCartao;
import microservicecartoes.mcscartoes.infra.repository.ClienteCartaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository clienteCartaoRepository;


    public List<ClienteCartao> listCartoesByCpf(String cpf) {
        return clienteCartaoRepository.findByCpf(cpf);
    }
}
