package com.kivi.banking;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

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

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
}
