package com.kivi.banking.resource;

import com.kivi.banking.config.SystemMessage;
import com.kivi.banking.representation.TransferDetail;
import com.kivi.banking.service.AccountService;
import com.kivi.banking.service.TransferService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TransferResourceTest {
    private static TransferService transferService = mock(TransferService.class);
    private static AccountService accountService = mock(AccountService.class);
    private TransferDetail validTransferDetail;

    @ClassRule
    public static final ResourceTestRule resource = ResourceTestRule.builder()
            .addResource(new TransferResource(accountService, transferService))
            .build();

    @Before
    public void before() {
        validTransferDetail = TransferDetail.builder()
                .id(500L)
                .amount(BigDecimal.TEN)
                .borrowerAccountId(999L)
                .lenderAccountId(1000L)
                .build();
    }

    @After
    public void afterEach() {
        resetServices();
    }

    @Test
    public void shouldReturnFailedWhenBorrowerAccountIdIsNull() {
        TransferDetail invalidTransferDetail = TransferDetail.builder()
                .id(100L)
                .lenderAccountId(99L)
                .amount(BigDecimal.TEN)
                .build();

        Response response = resource.target("/transfer")
                .request()
                .post(Entity.json(invalidTransferDetail));

        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, response.getStatus());
        verifyZeroInteractions(accountService);
        verifyZeroInteractions(transferService);
    }

    @Test
    public void shouldReturnFailedWhenLenderAccountIdIsNull() {
        TransferDetail invalidTransferDetail = TransferDetail.builder()
                .id(100L)
                .borrowerAccountId(99L)
                .amount(BigDecimal.TEN)
                .build();

        Response response = resource.target("/transfer")
                .request()
                .post(Entity.json(invalidTransferDetail));

        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, response.getStatus());
        verifyZeroInteractions(accountService);
        verifyZeroInteractions(transferService);
    }

    @Test
    public void shouldReturnFailedWhenIdIsNull() {
        TransferDetail invalidTransferDetail = TransferDetail.builder()
                .borrowerAccountId(99L)
                .lenderAccountId(100L)
                .amount(BigDecimal.TEN)
                .build();

        Response response = resource.target("/transfer")
                .request()
                .post(Entity.json(invalidTransferDetail));

        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, response.getStatus());
        verifyZeroInteractions(accountService);
        verifyZeroInteractions(transferService);
    }

    @Test
    public void shouldReturnFailedWhenAmountIsNull() {
        TransferDetail invalidTransferDetail = TransferDetail.builder()
                .id(999L)
                .borrowerAccountId(99L)
                .lenderAccountId(100L)
                .build();

        Response response = resource.target("/transfer")
                .request()
                .post(Entity.json(invalidTransferDetail));

        assertNotNull(response);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, response.getStatus());
        verifyZeroInteractions(accountService);
        verifyZeroInteractions(transferService);
    }

    @Test
    public void shouldReturnFailedWhenLenderAccountNotExist() {
        when(accountService.checkAccountExists(validTransferDetail.getLenderAccountId())).thenReturn(false);

        Response response = resource.target("/transfer")
                .request()
                .post(Entity.json(validTransferDetail));

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND_404, response.getStatus());
        verify(accountService).checkAccountExists(validTransferDetail.getLenderAccountId());
        verifyNoMoreInteractions(accountService);
        verifyZeroInteractions(transferService);

        String responseMessage = response.readEntity(String.class);
        assertEquals(SystemMessage.ResourceResponse.LENDER_NOT_EXIST, responseMessage);
    }

    @Test
    public void shouldReturnFailedWhenAccountBalanceNotEnough() {
        when(accountService.checkAccountExists(validTransferDetail.getLenderAccountId())).thenReturn(true);
        when(accountService.isAccountBalanceEnoughForTransfer(validTransferDetail.getAmount(),
                validTransferDetail.getLenderAccountId()))
                .thenReturn(false);

        Response response = resource.target("/transfer")
                .request()
                .post(Entity.json(validTransferDetail));

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_ACCEPTABLE_406, response.getStatus());
        verify(accountService).checkAccountExists(validTransferDetail.getLenderAccountId());
        verify(accountService).isAccountBalanceEnoughForTransfer(validTransferDetail.getAmount(),
                validTransferDetail.getLenderAccountId());
        verifyNoMoreInteractions(accountService);
        verifyZeroInteractions(transferService);

        String responseMessage = response.readEntity(String.class);
        assertEquals(SystemMessage.ResourceResponse.NOT_ENOUGH_BALANCE, responseMessage);
    }

    @Test
    public void shouldMakeTransferWhenTransferDetailIsValid() {
        when(accountService.checkAccountExists(validTransferDetail.getLenderAccountId())).thenReturn(true);
        when(accountService.isAccountBalanceEnoughForTransfer(validTransferDetail.getAmount(),
                validTransferDetail.getLenderAccountId()))
                .thenReturn(true);

        Response response = resource.target("/transfer")
                .request()
                .post(Entity.json(validTransferDetail));

        assertNotNull(response);
        assertEquals(HttpStatus.OK_200, response.getStatus());
        verify(accountService).checkAccountExists(validTransferDetail.getLenderAccountId());
        verify(accountService).isAccountBalanceEnoughForTransfer(validTransferDetail.getAmount(),
                validTransferDetail.getLenderAccountId());
        verify(transferService).applyTransfer(validTransferDetail);

        String responseMessage = response.readEntity(String.class);
        assertEquals(SystemMessage.ResourceResponse.SUBMITTED, responseMessage);
    }

    private void resetServices() {
        reset(accountService);
        reset(transferService);
    }

}