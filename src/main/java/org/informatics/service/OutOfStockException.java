package org.informatics.service;


public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String goodID, int requested, int available) {
        super("Good " + goodID + ": requested " + requested + ", but only " + available + " available.");
    }
}
