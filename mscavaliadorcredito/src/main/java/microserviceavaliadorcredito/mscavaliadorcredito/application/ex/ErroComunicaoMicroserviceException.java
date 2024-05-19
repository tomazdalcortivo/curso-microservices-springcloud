package microserviceavaliadorcredito.mscavaliadorcredito.application.ex;

import lombok.Getter;

public class ErroComunicaoMicroserviceException extends Exception {

    @Getter
    private Integer status;

    public ErroComunicaoMicroserviceException(String msg, Integer status) {
        super(msg);
        this.status = status;
    }
}
