package com.kivi.banking.service.impl;

import com.kivi.banking.representation.TransferDetail;
import com.kivi.banking.service.AccountService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceImplTest {

    public Queue<TransferDetail> transferDetailQueue;

    @Mock
    public AccountService accountService;

    @InjectMocks
    public TransferServiceImpl service;

    private TransferDetail validTransferDetail;
    private TransferDetail invalidTransferDetail;

    @Before
    public void before() {
        transferDetailQueue = mock(LinkedList.class);
        service.setTransferDetailQueue(transferDetailQueue);

        validTransferDetail = TransferDetail.builder()
                .amount(BigDecimal.TEN)
                .borrowerAccountId(999L)
                .lenderAccountId(1000L)
                .id(88L)
                .build();

        invalidTransferDetail = TransferDetail.builder()
                .amount(BigDecimal.ONE)
                .borrowerAccountId(null)
                .lenderAccountId(123L)
                .build();
    }

    @After
    public void after() {
        reset(transferDetailQueue);
    }

    @Test
    public void shouldApplyTransfer() {
        service.applyTransfer(validTransferDetail);

        verify(accountService).deductMoneyFromAccount(validTransferDetail.getAmount(), validTransferDetail.getLenderAccountId());

        verify(transferDetailQueue).add(validTransferDetail);
    }

    @Test
    public void shouldReturnNextDetail() {
        when(transferDetailQueue.poll()).thenReturn(validTransferDetail);

        TransferDetail nextDetail = service.getNextDetail();

        assertNotNull(nextDetail);
        assertEquals(validTransferDetail, nextDetail);
    }
}