package com.kivi.banking.resource;

import com.kivi.banking.config.SystemMessage;
import com.kivi.banking.representation.Account;
import com.kivi.banking.service.AccountService;
import lombok.NonNull;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    private final AccountService accountService;

    @Inject
    public AccountResource(@NonNull AccountService accountService) {
        this.accountService = accountService;
    }

    @POST
    public Response createAccount(@Valid Account account) {
        if(accountService.checkAccountExists(account.getId())) {
            return Response
                    .status(HttpStatus.UNPROCESSABLE_ENTITY_422)
                    .entity(SystemMessage.ResourceResponse.ACCOUNT_ID_EXISTS)
                    .build();
        }

        accountService.createAccount(account);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    public Account getAccountDetails(@PathParam("id") long id) {
        return accountService.getAccountById(id);
    }

    @GET
    public List<Account> getAll() {
        return accountService.getAll();
    }
}