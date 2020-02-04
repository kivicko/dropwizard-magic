package com.kivi.banking.config.annotation.validator;

import com.kivi.banking.config.annotation.AccountDifferenceRule;
import com.kivi.banking.representation.TransferDetail;
import org.apache.commons.lang3.BooleanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountDifferenceRuleValidator implements ConstraintValidator<AccountDifferenceRule, TransferDetail> {

    @Override
    public void initialize(AccountDifferenceRule date) {
        // Nothing here
    }

    @Override
    public boolean isValid(TransferDetail detail, ConstraintValidatorContext constraintValidatorContext) {
        if (detail.getLenderAccountId() == null || detail.getBorrowerAccountId() == null) {
            return false;
        }

        return BooleanUtils.isFalse(detail.getLenderAccountId().equals(detail.getBorrowerAccountId()));
    }
}