package com.kivi.banking.config;

public class SystemMessage {
    public static final String MIN_TRANSFER_AMOUNT = "should be greater than zero.";
    public static final String ACCOUNT_NUMBER_MIN = "at least 5 letter length.";

    public static final String BORROWER_NOT_EXIST = "Borrower account does not exist, refunding to lender. " +
            "Borrower id: {} , lender id : {} , amount: {}";

    public static final String AMOUNT_TRANSFERRED = "Amount (total : {}) added to borrower (id : {}) account.";

    public static class ResourceResponse {
        public static final String LENDER_NOT_EXIST = "Lender account does not exist.";
        public static final String NOT_ENOUGH_BALANCE = "Not enough balance.";
        public static final String SUBMITTED = "Your request submitted.";
        public static final Object ACCOUNT_ID_EXISTS = "Account id already exists.";
    }
}