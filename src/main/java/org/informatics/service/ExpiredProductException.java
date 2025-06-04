package org.informatics.service;


public class ExpiredProductException extends RuntimeException {
    public ExpiredProductException(Good good) {
        super("Product " + good.getName() + " (ID=" + good.getId() + ") has expired on " + good.getExpirationDate());
    }
}