package com.kivi.banking.resource;

import com.kivi.banking.representation.TransferDetail;
import com.kivi.banking.service.AccountService;
import com.kivi.banking.service.TransferService;
import lombok.NonNull;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.*;

@Path("/transfer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransferResource {

    private final AccountService accountService;
    private final TransferService transferService;

    @Inject
    public TransferResource(@NonNull AccountService accountService, @NonNull TransferService transferService) {
        this.accountService = accountService;
        this.transferService = transferService;
    }

    @POST
    public Response makeTransfer(TransferDetail transferDetail) {
        if(!accountService.isAccountBalanceEnoughForTransfer(transferDetail.getAmount(), transferDetail.getLenderAccountId())) {
            return Response.status(Status.NOT_ACCEPTABLE).entity("Not enough balance.").build();
        }
        transferService.applyTransfer(transferDetail);

        return Response.ok("Your request submitted").build();
    }
}
