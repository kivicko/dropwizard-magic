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
public class TransferDetail {
    private long id;
    private long borrowerAccountId;
    private long lenderAccountId;
    private BigDecimal amount;
}