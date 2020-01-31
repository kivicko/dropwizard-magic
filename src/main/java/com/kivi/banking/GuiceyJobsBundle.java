package com.kivi.banking;

import io.dropwizard.jobs.GuiceJobManager;
import io.dropwizard.jobs.JobConfiguration;
import io.dropwizard.jobs.JobManager;
import io.dropwizard.jobs.JobsBundle;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class GuiceyJobsBundle extends JobsBundle {

    private GuiceBundle guiceBundle;

    public GuiceyJobsBundle(GuiceBundle guiceBundle) {
        this.guiceBundle = guiceBundle;
    }

    @Override
    public void run(JobConfiguration configuration, Environment environment) {
        JobManager jobManager = new GuiceJobManager(configuration, guiceBundle.getInjector());
        environment.lifecycle().manage(jobManager);
    }
}
