package microservicecartoes.mcscartoes.representation;

import lombok.Data;
import microservicecartoes.mcscartoes.domain.BandeiraCartao;
import microservicecartoes.mcscartoes.domain.Cartao;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toModel() {
        return new Cartao(nome, bandeira, renda, limite);
    }

}
