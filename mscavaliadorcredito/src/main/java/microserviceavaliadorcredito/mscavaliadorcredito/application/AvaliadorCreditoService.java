package microserviceavaliadorcredito.mscavaliadorcredito.application;


import lombok.RequiredArgsConstructor;
import microserviceavaliadorcredito.mscavaliadorcredito.domain.model.CartoesCliente;
import microserviceavaliadorcredito.mscavaliadorcredito.domain.model.DadosCliente;
import microserviceavaliadorcredito.mscavaliadorcredito.domain.model.SituacaoCliente;
import microserviceavaliadorcredito.mscavaliadorcredito.infra.clients.CartoesResourceClient;
import microserviceavaliadorcredito.mscavaliadorcredito.infra.clients.ClienteResourceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientClient;

    private final CartoesResourceClient cartoesClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) {
        // obter dados do cliente - MSClientes
        // obter cartoes do cliente - MSCartoes

        ResponseEntity<DadosCliente> dadosClienteResponse = clientClient.dadosCliente(cpf);
        ResponseEntity<List<CartoesCliente>> cartaoResponse = cartoesClient.getCartoesPorCliente(cpf);
        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .cartoes(cartaoResponse.getBody())
                .build();
    }


}
