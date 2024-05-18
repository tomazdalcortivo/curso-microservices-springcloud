package microservicecartoes.mcscartoes.application;

import lombok.RequiredArgsConstructor;
import microservicecartoes.mcscartoes.domain.Cartao;
import microservicecartoes.mcscartoes.infra.repository.CartaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {


    private final CartaoRepository cartaoRepository;

    @Transactional
    public Cartao salvar(Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

    public List<Cartao> getCartaoRendaMenorIgual(Long renda){
        var rendaBigDecimal = BigDecimal.valueOf(renda);
        return cartaoRepository.findByRendaLessThanEqual(rendaBigDecimal);
    }
}
