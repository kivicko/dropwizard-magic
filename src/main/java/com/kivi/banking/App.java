package com.kivi.banking;

import com.kivi.banking.resource.AccountResource;
import com.kivi.banking.resource.TransferResource;
import com.kivi.banking.service.AccountService;
import com.kivi.banking.service.TransferService;
import com.kivi.banking.service.impl.AccountServiceImpl;
import com.kivi.banking.service.impl.TransferServiceImpl;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.jobs.GuiceJobsBundle;
import io.dropwizard.jobs.Job;
import io.dropwizard.jobs.JobsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import ru.vyarus.dropwizard.guice.GuiceBundle;

import javax.inject.Singleton;

public class App extends Application<BankingConfiguration> {

    @Override
    public void run(BankingConfiguration bankingConfiguration, Environment environment) throws Exception {
    }

    @Override
    public void initialize(Bootstrap<BankingConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(false, true)
        ));

        GuiceBundle<Configuration> guiceBundle = GuiceBundle.builder()
                .bindConfigurationInterfaces()
                .modules(new DIModule())
                .enableAutoConfig(getClass().getPackage().getName())
                .build();
        bootstrap.addBundle(guiceBundle);
    }

    public static void main(String[] args ) throws Exception {
        new App().run(args);
    }
}
