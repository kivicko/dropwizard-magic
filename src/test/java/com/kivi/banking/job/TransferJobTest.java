package com.kivi.banking.job;

import com.kivi.banking.representation.TransferDetail;
import com.kivi.banking.service.AccountService;
import com.kivi.banking.service.TransferService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class TransferJobTest {

    @Mock
    private TransferService transferService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransferJob job;

    private TransferDetail validTransferDetail;

    @Before
    public void before() {
        validTransferDetail = TransferDetail.builder()
                .id(500L)
                .amount(BigDecimal.TEN)
                .borrowerAccountId(999L)
                .lenderAccountId(1000L)
                .build();
    }

    @Test
    public void shouldDoNothingWhenQueueIsempty() {
        when(transferService.getNextDetail()).thenReturn(null);

        job.doJob(null);

        verifyZeroInteractions(accountService);
        verify(transferService).getNextDetail();
    }

    @Test
    public void shouldAddAmountToAccountWhenBorrowerAccountExists() {
        when(transferService.getNextDetail()).thenReturn(validTransferDetail);
        when(accountService.checkAccountExists(validTransferDetail.getBorrowerAccountId()))
                .thenReturn(true);

        job.doJob(null);

        verify(transferService).getNextDetail();
        verify(accountService).checkAccountExists(validTransferDetail.getBorrowerAccountId());
        verify(accountService).addAmountToAccount(validTransferDetail.getAmount(), validTransferDetail.getBorrowerAccountId());
        verify(transferService, times(0)).applyTransfer(any());
    }

    @Test
    public void shouldCreateNewTransferDetailWhenBorrowerAccountNotExist() {
        when(transferService.getNextDetail()).thenReturn(validTransferDetail);
        when(accountService.checkAccountExists(validTransferDetail.getBorrowerAccountId()))
                .thenReturn(false);

        job.doJob(null);

        verify(transferService).getNextDetail();
        verify(accountService).checkAccountExists(validTransferDetail.getBorrowerAccountId());
        verify(accountService, times(0)).addAmountToAccount(validTransferDetail.getAmount(), validTransferDetail.getBorrowerAccountId());
        verify(transferService).applyTransfer(any());
    }


}