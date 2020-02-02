package com.kivi.banking.service;

import com.kivi.banking.representation.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    List<Account> getAll();

    Account getAccountById(Long id);

    boolean checkAccountExists(Long id);

    void saveAccount(Account account);

    void addAmountToAccount(BigDecimal amount, Long accountId);

    boolean isAccountBalanceEnoughForTransfer(BigDecimal amount, Long accountId);

    void deductMoneyFromAccount(BigDecimal amount, Long lenderAccountId);
}