package com.kivi.banking.representation;

import com.kivi.banking.config.SystemMessage;
import com.kivi.banking.config.annotation.AccountDifferenceRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AccountDifferenceRule
public class TransferDetail {
    private Long id;

    @NotNull
    private Long borrowerAccountId;

    @NotNull
    private Long lenderAccountId;

    @NotNull
    @DecimalMin(value = "0.01", message = SystemMessage.MIN_TRANSFER_AMOUNT)
    private BigDecimal amount;
}