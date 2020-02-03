package com.kivi.banking.representation;

import com.kivi.banking.config.SystemMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 5, message = SystemMessage.ACCOUNT_NUMBER_MIN)
    private String accountNumber;

    @NotNull
    @Min(value = 0)
    private BigDecimal amount;

    public void addAmount(BigDecimal addition) {
        amount = amount.add(addition);
    }

    public void subtractAmount(BigDecimal deduction) {
        amount = amount.subtract(deduction);
    }
}