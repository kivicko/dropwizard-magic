package com.kivi.banking.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private long id;
    private String accountNumber;
    private BigDecimal amount;

    public void addAmount(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }
}