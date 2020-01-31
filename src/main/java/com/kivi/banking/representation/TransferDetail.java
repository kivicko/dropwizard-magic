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
    private Long id;
    private Long borrowerAccountId;
    private Long lenderAccountId;
    private BigDecimal amount;
}