package com.kivi.banking.config;

public class SystemMessage {
    public static final String MIN_TRANSFER_AMOUNT = "should be greater than zero.";
    public static final String ACCOUNT_NUMBER_MIN = "at least 5 letter length.";

    public static final String TRANSFER_QUEUE_EMPTY = "Transfer queue is empty";
    public static final String BORROWER_NOT_EXIST ="Borrower account does not exist, refunding to lender. " +
            "Borrower id: {} , lender id : {} , amount: {}";

    public static final String AMOUNT_TRANSFERRED = "Amount (total : {}) added to borrower (id : {}) account.";

    public static class RESOURCE_RESPONSE {
        public static final String LENDER_NOT_EXIST = "Lender account does not exist.";
    }
}
