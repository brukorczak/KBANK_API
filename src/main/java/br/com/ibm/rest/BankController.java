package br.com.ibm.rest;

import br.com.ibm.persistence.dto.*;
import br.com.ibm.services.BankService;
import br.com.ibm.services.annotation.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/v1/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BankController {
    @Inject
    BankService bankService;

    @POST
    @Path("/{id}/accounts")
    public Response createAccount(@Valid AddAccountDto addAccountDto, @PathParam("id") Long userId) {
        this.bankService.createAccount(userId, addAccountDto.getAccountType().toString());
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/balances/{id}")
    @Authenticated
    public Response balanceList(@PathParam("id") Long id) {
        List<AccountDto> accountRequestList = this.bankService.balanceList(id);
        return Response.status(Response.Status.OK).entity(accountRequestList).build();
    }

    @POST
    @Path("/deposit")
    @Authenticated
    public Response deposit(@Valid DepositDto depositRequest) {
        bankService.deposit(depositRequest.getAccountNumber(), depositRequest.getValue());
        String message = String.format("Depósito no valor de %.2f na conta de número %s.", depositRequest.getValue(), depositRequest.getAccountNumber());
        return Response.status(Response.Status.OK).entity(message).build();
    }

    @POST
    @Path("/withdraw")
    @Authenticated
    public Response withdraw(@Valid WithdrawDto withdrawRequest) {
        double newBalance = bankService.withdraw(withdrawRequest.getAccountNumber(), withdrawRequest.getValue());
        String message = String.format("Saque no valor de %.2f na conta de número %s. Seu Saldo atual é R$ %.2f.", withdrawRequest.getValue(), withdrawRequest.getAccountNumber(), newBalance);
        return Response.status(Response.Status.OK).entity(message).build();
    }

    @PATCH
    @Path("/transfer")
    @Authenticated
    public Response transfer(@Valid TransferDto transferRequest) {
        bankService.transfer(transferRequest.getSourceAccountNumber(), transferRequest.getTargetAccountNumber(), transferRequest.getValue());
        String message = String.format("Conta %s fez uma transferência de R$ %.2f para conta %s.", transferRequest.getSourceAccountNumber(), transferRequest.getValue(), transferRequest.getTargetAccountNumber());
        return Response.status(Response.Status.OK).entity(message).build();
    }
}
