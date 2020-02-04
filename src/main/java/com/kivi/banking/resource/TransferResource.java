package com.kivi.banking.resource;

import com.kivi.banking.config.SystemMessage;
import com.kivi.banking.representation.TransferDetail;
import com.kivi.banking.service.AccountService;
import com.kivi.banking.service.TransferService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/transfer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class TransferResource {

    private final AccountService accountService;
    private final TransferService transferService;

    @Inject
    public TransferResource(@NonNull AccountService accountService, @NonNull TransferService transferService) {
        this.accountService = accountService;
        this.transferService = transferService;
    }

    @POST
    public Response makeTransfer(@Valid TransferDetail transferDetail) {
        if (!accountService.checkAccountExists(transferDetail.getLenderAccountId())) {
            return Response.status(Status.NOT_FOUND).entity(SystemMessage.RESOURCE_RESPONSE.LENDER_NOT_EXIST).build();
        }
        if (!accountService.isAccountBalanceEnoughForTransfer(transferDetail.getAmount(), transferDetail.getLenderAccountId())) {
            return Response.status(Status.NOT_ACCEPTABLE).entity(SystemMessage.RESOURCE_RESPONSE.NOT_ENOUGH_BALANCE).build();
        }
        transferService.applyTransfer(transferDetail);

        return Response.ok(SystemMessage.RESOURCE_RESPONSE.SUBMITTED).build();
    }
}