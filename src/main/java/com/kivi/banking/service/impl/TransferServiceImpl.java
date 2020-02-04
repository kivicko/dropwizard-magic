package com.kivi.banking.service.impl;

import com.google.inject.Singleton;
import com.kivi.banking.representation.TransferDetail;
import com.kivi.banking.service.AccountService;
import com.kivi.banking.service.TransferService;
import lombok.NonNull;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.Queue;

@Singleton
public class TransferServiceImpl implements TransferService {

    public Queue<TransferDetail> transferDetailQueue = new LinkedList<>();

    private final AccountService accountService;

    @Inject
    public TransferServiceImpl(@NonNull AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void applyTransfer(TransferDetail transferDetail) {
        accountService.deductMoneyFromAccount(transferDetail.getAmount(), transferDetail.getLenderAccountId());
        transferDetailQueue.add(transferDetail);
    }

    @Override
    public TransferDetail getNextDetail() {
        return transferDetailQueue.poll();
    }

    //for testing purposes.
    public void setTransferDetailQueue(Queue<TransferDetail> queue) {
        transferDetailQueue = queue;
    }
}