package com.kivi.banking.job;

import com.kivi.banking.config.SystemMessage;
import com.kivi.banking.representation.TransferDetail;
import com.kivi.banking.service.AccountService;
import com.kivi.banking.service.TransferService;
import io.dropwizard.jobs.Job;
import io.dropwizard.jobs.annotations.Every;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;
import java.math.BigDecimal;

@Slf4j
@Every("1s")
public class TransferJob extends Job {

    private AccountService accountService;
    private TransferService transferService;

    @Inject
    public TransferJob(@NonNull AccountService accountService, @NonNull TransferService transferService) {
        this.accountService = accountService;
        this.transferService = transferService;
    }

    @Override
    public void doJob(JobExecutionContext context) {
        TransferDetail nextDetail = transferService.getNextDetail();
        if(nextDetail == null) {
            return;
        }
        BigDecimal amount = nextDetail.getAmount();
        Long borrowerAccountId = nextDetail.getBorrowerAccountId();
        Long lenderAccountId = nextDetail.getLenderAccountId();

        if(accountService.checkAccountExists(borrowerAccountId)) {
            accountService.addAmountToAccount(amount, borrowerAccountId);
            log.info(SystemMessage.AMOUNT_TRANSFERRED, amount, borrowerAccountId);
        } else {
            log.info(SystemMessage.BORROWER_NOT_EXIST, borrowerAccountId, lenderAccountId, amount);
            createTransferActionToRefund(lenderAccountId, amount);
        }
    }

    private void createTransferActionToRefund(Long lenderAccountId, BigDecimal amount) {
        transferService.applyTransfer(TransferDetail.builder()
                .amount(amount)
                .lenderAccountId(lenderAccountId)
                .borrowerAccountId(lenderAccountId)
                .build()
        );
    }
}
