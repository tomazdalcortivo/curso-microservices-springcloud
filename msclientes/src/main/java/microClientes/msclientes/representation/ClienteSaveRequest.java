package microClientes.msclientes.representation;

import lombok.Data;
import microClientes.msclientes.domain.Cliente;

@Data
public class ClienteSaveRequest {

    private String cpf;
    private String nome;
    private String idade;

    public Cliente toModel() {
        return new Cliente(cpf, nome, idade);
    }
}
