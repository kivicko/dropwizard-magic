package com.kivi.banking.service;

import com.kivi.banking.representation.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AccountService {

    private ConcurrentHashMap<Long, Account> accountMap;

    public AccountService() {
        Account acc1 = Account.builder()
                .amount(new BigDecimal(100))
                .id(10L)
                .build();

        Account acc2 = Account.builder()
                .amount(new BigDecimal(500))
                .id(11L)
                .build();


        this.accountMap = new ConcurrentHashMap<>();
        accountMap.put(acc1.getId(), acc1);
        accountMap.put(acc2.getId(), acc2);
    }

    public List<Account> getAll() {
        return new ArrayList<>(accountMap.values());
    }

    public Account getAccountById(Long id) {
        return accountMap.get(id);
    }

    public void saveAccount(Account account) {
        accountMap.put(account.getId(), account);
    }
}
