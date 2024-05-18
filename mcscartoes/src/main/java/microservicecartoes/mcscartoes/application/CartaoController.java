package microservicecartoes.mcscartoes.application;


import lombok.RequiredArgsConstructor;
import microservicecartoes.mcscartoes.domain.Cartao;
import microservicecartoes.mcscartoes.representation.CartaoSaveRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartaoController {

    private final CartaoService cartaoService;

    @GetMapping
    public String status() {
        return "OK";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request) {
        Cartao cartao = request.toModel();
        cartaoService.salvar(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
