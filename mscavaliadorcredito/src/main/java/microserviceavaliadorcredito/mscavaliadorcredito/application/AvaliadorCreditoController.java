package microserviceavaliadorcredito.mscavaliadorcredito.application;


import lombok.RequiredArgsConstructor;
import microserviceavaliadorcredito.mscavaliadorcredito.application.ex.DadosClienteNotFoundException;
import microserviceavaliadorcredito.mscavaliadorcredito.application.ex.ErroComunicaoMicroserviceException;
import microserviceavaliadorcredito.mscavaliadorcredito.domain.model.DadosAvaliacao;
import microserviceavaliadorcredito.mscavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import microserviceavaliadorcredito.mscavaliadorcredito.domain.model.SituacaoCliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/avaliacoes-credito")
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @GetMapping(value = "situação-cliente", params = "cpf")
    public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
        SituacaoCliente situacaoCliente = null;
        try {
            situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicaoMicroserviceException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public  ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dados){
        try {
            RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService
                    .realizarAvaliacao(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(retornoAvaliacaoCliente);
        }catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }catch (ErroComunicaoMicroserviceException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }
}
