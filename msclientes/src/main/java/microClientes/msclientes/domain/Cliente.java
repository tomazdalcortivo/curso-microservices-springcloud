package microClientes.msclientes.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String cpf;
    private String nome;
    private Integer idade;


    public Cliente(String cpf, String nome, String idade) {
        this.cpf = cpf;
        this.nome = nome;
        this.idade = Integer.parseInt(idade);
    }
}
