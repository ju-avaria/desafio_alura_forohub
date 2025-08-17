package com.aluracursos.forumhub.infra.errors;

import java.io.Serial;

public class IntegrityValidation extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    public IntegrityValidation(String mensaje) {
        super(mensaje);
    }
}
