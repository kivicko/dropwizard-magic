package com.kivi.banking.resource;

import com.kivi.banking.representation.Account;
import com.kivi.banking.service.AccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    private final AccountService accountService;

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Path("/{id}")
    public Response getAccountDetails(@PathParam("id") long id) {
        return Response.ok(accountService.getAccountById(id)).build();
    }

    @GET
    public List<Account> getAll(){
        return accountService.getAll();
    }
}