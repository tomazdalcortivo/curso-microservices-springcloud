package microserviceavaliadorcredito.mscavaliadorcredito.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartoesCliente {

    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

}