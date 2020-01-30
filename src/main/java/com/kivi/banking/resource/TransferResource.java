package com.kivi.banking.resource;

import com.kivi.banking.representation.TransferDetail;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/transfer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransferResource {

    @POST
    public Response makeTransfer(TransferDetail transferDetail) {

        System.out.println(transferDetail.toString());
        return Response.ok().build();
    }
}
