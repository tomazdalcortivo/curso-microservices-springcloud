package microClientes.msclientes.application;

import lombok.RequiredArgsConstructor;
import microClientes.msclientes.domain.Cliente;
import microClientes.msclientes.infra.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

//    @Transactional
//    public void salvar(Cliente cliente) {
//        clienteRepository.save(cliente);
//    }

    @Transactional
    public Cliente salvar(Cliente cliente){
        return clienteRepository.save(cliente);
    }

//    @Transactional
    public Optional<Cliente> getByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }
}
