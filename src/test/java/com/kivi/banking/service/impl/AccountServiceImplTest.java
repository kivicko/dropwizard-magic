package com.kivi.banking.service.impl;

import com.kivi.banking.representation.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4ClassRunner.class)
public class AccountServiceImplTest {

    private ConcurrentHashMap<Long, Account> map;
    private AccountServiceImpl service = new AccountServiceImpl();
    private Account validAccount;
    private Account invalidAccount;

    @Before
    public void before() {
        map = mock(ConcurrentHashMap.class);
        service.setAccountMap(map);

        validAccount = Account.builder()
                .accountNumber("sampleAccountNumber")
                .amount(BigDecimal.TEN)
                .id(999L)
                .build();

        invalidAccount = Account.builder()
                .amount(null)
                .id(1L)
                .build();
    }

    @After
    public void after() {
        reset(map);
    }

    @Test
    public void shouldReturnAllAccounts() {
        when(map.values()).thenReturn(Arrays.asList(validAccount, invalidAccount));

        List<Account> allAccounts = service.getAll();

        assertNotNull(allAccounts);
        assertEquals(2, allAccounts.size());
        assertTrue(allAccounts.contains(validAccount));
        assertTrue(allAccounts.contains(invalidAccount));
        verify(map).values();
    }

    @Test
    public void shouldReturnEmptyListWhenNoAccountsExist() {
        when(map.values()).thenReturn(new ArrayList<>());

        List<Account> allAccounts = service.getAll();

        assertNotNull(allAccounts);
        assertEquals(0, allAccounts.size());
        verify(map).values();
    }

    @Test
    public void shouldReturnAccountWhenGivenIdExist() {
        when(map.get(validAccount.getId())).thenReturn(validAccount);

        Account responseAccount = service.getAccountById(validAccount.getId());

        assertNotNull(responseAccount);
        assertEquals(responseAccount, validAccount);
        verify(map).get(validAccount.getId());
    }

    @Test
    public void shouldReturnNullWhenGivenIdNotExist() {
        when(map.get(validAccount.getId())).thenReturn(null);

        Account responseAccount = service.getAccountById(validAccount.getId());

        assertNull(responseAccount);
        verify(map).get(validAccount.getId());
    }

    @Test
    public void shouldReturnTrueWhenGivenAccountIdExist() {
        when(map.containsKey(validAccount.getId())).thenReturn(true);

        boolean result = service.checkAccountExists(validAccount.getId());

        assertTrue(result);
        verify(map).containsKey(validAccount.getId());
    }

    @Test
    public void shouldReturnFalseWhenGivenAccountIdNotExist() {
        when(map.containsKey(validAccount.getId())).thenReturn(false);

        boolean result = service.checkAccountExists(validAccount.getId());

        assertFalse(result);
        verify(map).containsKey(validAccount.getId());
    }

    @Test
    public void shouldAddAccountToMap() {
        service.createAccount(validAccount);

        verify(map).put(validAccount.getId(), validAccount);
    }

    @Test
    public void shouldAddAmountToAccount() {
        when(map.get(validAccount.getId())).thenReturn(validAccount);

        service.addAmountToAccount(BigDecimal.TEN, validAccount.getId());

        verify(map).get(validAccount.getId());
        assertEquals(new BigDecimal(20), validAccount.getAmount());
    }

    @Test
    public void shouldReturnFalseWhenAccountIdNotExist() {
        when(map.containsKey(validAccount.getId())).thenReturn(false);

        boolean response = service.isAccountBalanceEnoughForTransfer(BigDecimal.TEN, validAccount.getId());

        assertFalse(response);
        verify(map).containsKey(validAccount.getId());
    }

    @Test
    public void shouldReturnFalseWhenAccountBalanceIsNotEnoughForTransfer() {
        when(map.containsKey(validAccount.getId())).thenReturn(true);
        when(map.get(validAccount.getId())).thenReturn(validAccount);

        boolean response = service.isAccountBalanceEnoughForTransfer(new BigDecimal(999), validAccount.getId());

        assertFalse(response);
        verify(map).containsKey(validAccount.getId());
        verify(map).get(validAccount.getId());
    }

    @Test
    public void shouldReturnTrueWhenAccountBalanceIsEnoughForTransfer() {
        when(map.containsKey(validAccount.getId())).thenReturn(true);
        when(map.get(validAccount.getId())).thenReturn(validAccount);

        boolean response = service.isAccountBalanceEnoughForTransfer(BigDecimal.ONE, validAccount.getId());

        assertTrue(response);
        verify(map).containsKey(validAccount.getId());
        verify(map).get(validAccount.getId());
    }

    @Test
    public void shouldDeductMoneyFromAccount() {
        when(map.get(validAccount.getId())).thenReturn(validAccount);

        service.deductMoneyFromAccount(BigDecimal.TEN, validAccount.getId());

        verify(map).get(validAccount.getId());
        assertEquals(BigDecimal.ZERO, validAccount.getAmount());
    }
}