package com.kivi.banking.service;

import com.kivi.banking.representation.TransferDetail;

public interface TransferService {
    void applyTransfer(TransferDetail transferDetail);

    TransferDetail getNextDetail();
}
