package com.kivi.banking.job;

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

@Slf4j
@Every("5s")
public class TransferJob extends Job {

    private AccountService accountService;
    private TransferService transferService;

    @Inject
    public TransferJob(@NonNull AccountService accountService, @NonNull TransferService transferService) {
        this.accountService = accountService;
        this.transferService = transferService;
    }

    @Override
    public void doJob(JobExecutionContext context) throws JobExecutionException {
        TransferDetail nextDetail = transferService.getNextDetail();
        if(nextDetail != null)
            System.out.println(nextDetail.toString());
    }
}
