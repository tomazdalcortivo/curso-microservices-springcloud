package microserviceavaliadorcredito.mscavaliadorcredito.infra.clients;

import microserviceavaliadorcredito.mscavaliadorcredito.domain.model.CartoesCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscartoes", path = "/cartoes")
public interface CartoesResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CartoesCliente>> getCartoesPorCliente(@RequestParam("cpf") String cpf);

}
