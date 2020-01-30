package com.kivi.banking;

import com.kivi.banking.resource.AccountResource;
import com.kivi.banking.resource.TransferResource;
import com.kivi.banking.service.AccountService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

public class App extends Application<BankingConfiguration> {

    public static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void run(BankingConfiguration bankingConfiguration, Environment environment) throws Exception {
        AccountService accountService = new AccountService();

        environment.jersey().register(new AccountResource(accountService));
        environment.jersey().register(TransferResource.class);
        environment.jersey().register(new AbstractBinder() {
            @Override
            public void configure() {
                bindAsContract(AccountService.class).in(Singleton.class);
            }
        });
    }

    public static void main( String[] args ) throws Exception {
        new App().run(args);
    }
}
