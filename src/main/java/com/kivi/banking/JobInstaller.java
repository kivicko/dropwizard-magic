package com.kivi.banking;

import io.dropwizard.setup.Environment;
import org.quartz.Job;
import ru.vyarus.dropwizard.guice.module.installer.FeatureInstaller;
import ru.vyarus.dropwizard.guice.module.installer.install.TypeInstaller;
import ru.vyarus.dropwizard.guice.module.installer.util.FeatureUtils;
import ru.vyarus.dropwizard.guice.module.installer.util.Reporter;

public class JobInstaller implements FeatureInstaller<Job>, TypeInstaller<Job> {

    private final Reporter reporter = new Reporter(JobInstaller.class, "jobs =");

    @Override
    public boolean matches(Class<?> type) {
        return FeatureUtils.is(type, Job.class);
    }

    @Override
    public void install(Environment environment, Class<Job> type) {
        reporter.line("(%s)", type.getName());
    }

    @Override
    public void report() {
        reporter.report();
    }
}