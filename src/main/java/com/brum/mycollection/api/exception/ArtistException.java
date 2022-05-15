package com.brum.mycollection.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ArtistException extends RuntimeException {

    private static final long serialVersionUID = 4846131244714344880L;

    private final HttpStatus httpStatus;

    public ArtistException(final String mensagem, HttpStatus httpStatus) {
        super(mensagem);
        this.httpStatus = httpStatus;
    }


}