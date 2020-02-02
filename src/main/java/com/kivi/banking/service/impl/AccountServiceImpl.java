package com.kivi.banking.service.impl;

import com.google.inject.Singleton;
import com.kivi.banking.representation.Account;
import com.kivi.banking.service.AccountService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class AccountServiceImpl implements AccountService {

    private ConcurrentHashMap<Long, Account> accountMap;

    public AccountServiceImpl() {
        Account acc1 = Account.builder()
                .amount(new BigDecimal(100))
                .id(10L)
                .accountNumber("number 1")
                .build();

        Account acc2 = Account.builder()
                .amount(new BigDecimal(500))
                .accountNumber("number 2")
                .id(11L)
                .build();


        this.accountMap = new ConcurrentHashMap<Long, Account>();
        accountMap.put(acc1.getId(), acc1);
        accountMap.put(acc2.getId(), acc2);
    }

    @Override
    public List<Account> getAll() {
        return new ArrayList<>(accountMap.values());
    }

    @Override
    public Account getAccountById(Long id) {
        return accountMap.get(id);
    }

    @Override
    public boolean checkAccountExists(Long id) {
        return accountMap.containsKey(id);
    }

    @Override
    public void saveAccount(Account account) {
        accountMap.put(account.getId(), account);
    }

    @Override
    public void addAmountToAccount(BigDecimal amount, Long accountId) {

    }

    @Override
    public boolean isAccountBalanceEnoughForTransfer(BigDecimal amount, Long accountId) {
        if(accountMap.containsKey(accountId)) {
            Account account = accountMap.get(accountId);
            return account.getAmount().compareTo(amount) != -1;
        }
        return false;
    }

    @Override
    public void deductMoneyFromAccount(BigDecimal amount, Long lenderAccountId) {
        accountMap.get(lenderAccountId).addAmount(amount.negate());
    }
}
