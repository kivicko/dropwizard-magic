package com.kivi.banking.resource;

import com.kivi.banking.representation.Account;
import com.kivi.banking.service.AccountService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountResourceTest {

    private static AccountService accountService = mock(AccountService.class);

    @ClassRule
    public static final ResourceTestRule resource = ResourceTestRule.builder()
            .addResource(new AccountResource(accountService))
            .build();

    private Account validAccount;

    @Before
    public void before() {
        this.validAccount = Account.builder()
                .accountNumber("new account number")
                .amount(BigDecimal.TEN)
                .id(999L)
                .build();
    }

    @AfterEach
    public void afterEach() {
        reset(accountService);
    }

    @Test
    public void shouldCreateNewAccountWhenArgumentIsValid() {
        Response response = resource.target("/accounts")
                .request()
                .post(Entity.json(validAccount));

        assertNotNull(response);
        assertEquals(HttpStatus.OK_200, response.getStatus());
        verify(accountService).createAccount(validAccount);
    }

    @Test
    public void shouldReturnUnprocessableEntityWhenAmountNotDefined() {
        Account invalidAccount = Account.builder()
                .accountNumber("new account number")
                .id(999L)
                .build();

        Response response = resource.target("/accounts")
                .request()
                .post(Entity.json(invalidAccount));

        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, response.getStatus());
        verifyZeroInteractions(accountService);
    }

    @Test
    public void shouldReturnUnprocessableEntityWhenIDNotDefined() {
        Account invalidAccount = Account.builder()
                .accountNumber("new account number")
                .amount(BigDecimal.TEN)
                .build();

        Response response = resource.target("/accounts")
                .request()
                .post(Entity.json(invalidAccount));

        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, response.getStatus());
        verifyZeroInteractions(accountService);
    }

    @Test
    public void shouldReturnUnprocessableEntityWhenAccountNumberNotDefined() {
        Account invalidAccount = Account.builder()
                .id(999L)
                .amount(BigDecimal.TEN)
                .build();

        Response response = resource.target("/accounts")
                .request()
                .post(Entity.json(invalidAccount));

        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, response.getStatus());
        verifyZeroInteractions(accountService);
    }

    @Test
    public void shouldReturnAccountDetailsWhenGivenIdExists() {
        when(accountService.getAccountById(999L)).thenReturn(validAccount);

        Response response = resource.target("/accounts/999")
                .request()
                .get();

        assertNotNull(response);
        assertEquals(HttpStatus.OK_200, response.getStatus());

        Account responseAccount = response.readEntity(Account.class);

        assertNotNull(responseAccount);
        assertEquals(validAccount, responseAccount);
        verify(accountService).getAccountById(999L);
    }

    @Test
    public void shouldReturnNullWhenGivenIdNotExist() {
        when(accountService.getAccountById(999L)).thenReturn(null);

        Response response = resource.target("/accounts/999")
                .request()
                .get();

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT_204, response.getStatus());
        verify(accountService).getAccountById(999L);
    }

    @Test
    public void shouldReturnAllAccounts() {
        reset(accountService);

        Account someAccount = new Account();
        when(accountService.getAll()).thenReturn(Arrays.asList(validAccount, someAccount));

        Response response = resource.target("/accounts/")
                .request()
                .get();

        assertNotNull(response);
        assertEquals(HttpStatus.OK_200, response.getStatus());
        verify(accountService).getAll();

        List<Map<String, Object>> accountList = response.readEntity(List.class);

        assertNotNull(accountList);
        assertEquals(2, accountList.size());
        assertEquals(validAccount.getId().toString(), accountList.get(0).get("id").toString());
        assertEquals(accountList.get(0).get("accountNumber").toString(), validAccount.getAccountNumber().toString());
        assertEquals(accountList.get(0).get("amount").toString(), validAccount.getAmount().toString());
    }

    @Test
    public void shouldReturnNullWhenNoAccountExist() {
        when(accountService.getAll()).thenReturn(new ArrayList<>());

        Response response = resource.target("/accounts/")
                .request()
                .get();

        assertNotNull(response);
        assertEquals(HttpStatus.OK_200, response.getStatus());
        verify(accountService).getAll();

        List<Map<String, Object>> accountList = response.readEntity(List.class);

        assertNotNull(accountList);
        assertEquals(0, accountList.size());
    }
}