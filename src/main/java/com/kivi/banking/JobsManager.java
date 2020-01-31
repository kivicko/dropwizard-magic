package com.kivi.banking;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.dropwizard.jobs.GuiceJobManager;
import io.dropwizard.jobs.JobConfiguration;

@Singleton
public class JobsManager extends GuiceJobManager {

    @Inject
    public JobsManager(Injector injector, JobConfiguration configuration) {
        super(configuration, injector);
    }
}