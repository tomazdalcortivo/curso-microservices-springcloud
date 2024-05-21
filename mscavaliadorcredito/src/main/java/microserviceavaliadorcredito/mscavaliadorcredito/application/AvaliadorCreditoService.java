package microserviceavaliadorcredito.mscavaliadorcredito.application;


import feign.FeignException;
import lombok.RequiredArgsConstructor;
import microserviceavaliadorcredito.mscavaliadorcredito.application.ex.DadosClienteNotFoundException;
import microserviceavaliadorcredito.mscavaliadorcredito.application.ex.ErroComunicaoMicroserviceException;
import microserviceavaliadorcredito.mscavaliadorcredito.application.ex.ErroSolicitacaoCartaoException;
import microserviceavaliadorcredito.mscavaliadorcredito.domain.model.*;
import microserviceavaliadorcredito.mscavaliadorcredito.infra.clients.CartoesResourceClient;
import microserviceavaliadorcredito.mscavaliadorcredito.infra.clients.ClienteResourceClient;
import microserviceavaliadorcredito.mscavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientClient;

    private final CartoesResourceClient cartoesClient;

    private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicaoMicroserviceException {
        // obter dados do cliente - MSClientes
        // obter cartoes do cliente - MSCartoes
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientClient.dadosCliente(cpf);
            ResponseEntity<List<CartoeCliente>> cartaoResponse = cartoesClient.getCartoesPorCliente(cpf);
            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartaoResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == 404) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicaoMicroserviceException(e.getMessage(), status);
        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
            throws DadosClienteNotFoundException, ErroComunicaoMicroserviceException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> CartoesResponse = cartoesClient.getCartoesRendaAte(renda);

            List<Cartao> cartaos = CartoesResponse.getBody();
            var listaCartoesAprovados = cartaos.stream().map(cartao -> {

                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal rendaBD = BigDecimal.valueOf(renda);
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setNome(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);

                return aprovado;
            }).toList();

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == 404) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicaoMicroserviceException(e.getMessage(), status);
        }
    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados) {
        try {
            emissaoCartaoPublisher.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        } catch (Exception e) {
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }
    }
}
