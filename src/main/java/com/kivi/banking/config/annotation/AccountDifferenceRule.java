package com.kivi.banking.config.annotation;

import com.kivi.banking.config.annotation.validator.AccountDifferenceRuleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = {AccountDifferenceRuleValidator.class})
public @interface AccountDifferenceRule {

    String message() default "Lender & Borrower ID's must not be same.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}