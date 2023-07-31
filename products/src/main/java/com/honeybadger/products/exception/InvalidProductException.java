package com.honeybadger.products.exception;

import java.util.function.Supplier;

public class InvalidProductException extends RuntimeException{

    public InvalidProductException(String msg){
        super(msg);
    }
}
