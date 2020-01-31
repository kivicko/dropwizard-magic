package com.kivi.banking;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;
import io.dropwizard.jobs.JobConfiguration;
import ru.vyarus.dropwizard.guice.module.yaml.bind.Config;

import javax.validation.constraints.Max;

@Config
public class BankingConfiguration extends Configuration implements JobConfiguration {
    @JsonProperty
    @NotEmpty
    private String message;

    @JsonProperty
    @Max(10)
    private int messageRepetitions;

    @JsonProperty
    private String additionalMessage = "Optional message not assigned!";

    public String getMessage() {
        return this.message;
    }

    public int getMessageRepetitions() {
        return this.messageRepetitions;
    }

    public String getAdditionalMessage() {
        return this.additionalMessage;
    }
}