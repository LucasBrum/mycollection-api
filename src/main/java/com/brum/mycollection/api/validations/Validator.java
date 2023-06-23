package com.brum.mycollection.api.validations;

public interface Validator<T> {

    void validate(T t);
}
