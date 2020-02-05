package com.kivi.banking;

import com.kivi.banking.representation.Account;
import com.kivi.banking.representation.TransferDetail;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(DropwizardExtensionsSupport.class)
public class IntegrationTest {

    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("restful-bank.yml");

    private Account accountWithAmount;
    private Account accountWithZeroAmount;

    public static final DropwizardAppExtension<BankingConfiguration> RULE = new DropwizardAppExtension(
            App.class, CONFIG_PATH);

    @BeforeEach
    public void beforeEach() {
        accountWithAmount = Account.builder()
                .id(99L)
                .amount(BigDecimal.TEN)
                .accountNumber("integrationTestingAccountNumber")
                .build();

        accountWithZeroAmount = Account.builder()
                .amount(BigDecimal.ZERO)
                .accountNumber("sampleAccNum")
                .id(123456L)
                .build();
    }

    @BeforeAll
    public static void beforeAll() throws Exception {
        RULE.getApplication().run();
    }

    @Test
    public void shouldTransferAmountBetweenAccounts() throws Exception {
        Response savedAccountZeroAmount = saveAccount(accountWithZeroAmount);
        Response savedAccount = saveAccount(accountWithAmount);

        assertEquals(savedAccount.getStatusInfo().getStatusCode(), HttpStatus.OK_200);
        assertEquals(savedAccountZeroAmount.getStatusInfo().getStatusCode(), HttpStatus.OK_200);

        TransferDetail transferDetail = TransferDetail.builder()
                .borrowerAccountId(accountWithZeroAmount.getId())
                .lenderAccountId(accountWithAmount.getId())
                .amount(new BigDecimal(7))
                .id(222L)
                .build();

        Response transferResponse = makeTransfer(transferDetail);

        assertEquals(transferResponse.getStatusInfo().getStatusCode(), HttpStatus.OK_200);

        sleep(5000);

        Account accountWithAmountUpdated = getAccount(accountWithAmount.getId());

        assertEquals(new BigDecimal(3), accountWithAmountUpdated.getAmount());

        Account accountWithZeroAmountUpdated = getAccount(accountWithZeroAmount.getId());

        assertEquals(new BigDecimal(7), accountWithZeroAmountUpdated.getAmount());
    }

    private Response makeTransfer(TransferDetail transferDetail) {
        return RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/transfer")
                .request()
                .post(Entity.entity(transferDetail, MediaType.APPLICATION_JSON_TYPE));
    }

    private Response saveAccount(Account account) {
        return RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/accounts")
                .request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON_TYPE));
    }

    private Account getAccount(Long id) {
        return RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/accounts/" + id)
                .request()
                .get(Account.class);
    }

}
