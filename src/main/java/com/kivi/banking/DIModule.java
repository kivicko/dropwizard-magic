package com.kivi.banking;

import com.google.inject.AbstractModule;
import com.kivi.banking.service.AccountService;
import com.kivi.banking.service.TransferService;
import com.kivi.banking.service.impl.AccountServiceImpl;
import com.kivi.banking.service.impl.TransferServiceImpl;

public class DIModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountService.class).to(AccountServiceImpl.class);
        bind(TransferService.class).to(TransferServiceImpl.class);
    }
}
