package microserviceavaliadorcredito.mscavaliadorcredito.application;


import lombok.RequiredArgsConstructor;
import microserviceavaliadorcredito.mscavaliadorcredito.application.ex.DadosClienteNotFoundException;
import microserviceavaliadorcredito.mscavaliadorcredito.application.ex.ErroComunicaoMicroserviceException;
import microserviceavaliadorcredito.mscavaliadorcredito.domain.model.SituacaoCliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
