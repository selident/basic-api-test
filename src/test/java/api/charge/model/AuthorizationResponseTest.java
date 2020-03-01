package api.charge.model;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Charge Authorization Response Assert Tests
 */
public class AuthorizationResponseTest {

    @Test
    public void assertEquals_EqualsResponse_NoAssertionErrorTest() {

        AuthorizationResponseOutcome outcome = new AuthorizationResponseOutcome();
        outcome.setNetworkStatus("approved_by_network");
        outcome.setRiskLevel("normal");
        outcome.setRiskScore(64);
        outcome.setSellerMessage("Payment complete.");
        outcome.setType("authorized");

        AuthorizationResponseSource source = new AuthorizationResponseSource();
        source.setId("card_1DiD3z2eZvKYlo2CilH44yQY");
        source.setObject("card");
        source.setBrand("Visa");
        source.setCountry("US");
        source.setExpMonth(12);
        source.setExpYear(2019);
        source.setFingerprint("Xt5EWLLDS7FJjR1c");
        source.setFunding("credit");
        source.setLast4("4242");
        source.setMetadata(new AuthorizationResponseSourceMetadata());

        // Prepare expected response object
        AuthorizationResponse expectedResponse = new AuthorizationResponse();
        expectedResponse.setId("ch_1DiD402eZvKYlo2Cz8XicVgK");
        expectedResponse.setObject("charge");
        expectedResponse.setAmount(999f);
        expectedResponse.setCreated(1545018400);
        expectedResponse.setCurrency("usd");
        expectedResponse.setDescription("Successful charge");
        expectedResponse.setStatus("succeeded");

        expectedResponse.setOutcome(outcome);
        expectedResponse.setSource(source);

        // Prepare actual response object
        // Make sure two responses are the same
        AuthorizationResponse actualResponse = new AuthorizationResponse();
        actualResponse.setId(expectedResponse.getId());
        actualResponse.setObject(expectedResponse.getObject());
        actualResponse.setAmount(expectedResponse.getAmount());
        actualResponse.setCreated(expectedResponse.getCreated());
        actualResponse.setCurrency(expectedResponse.getCurrency());
        actualResponse.setDescription(expectedResponse.getDescription());
        actualResponse.setStatus(expectedResponse.getStatus());

        actualResponse.setOutcome(outcome);
        actualResponse.setSource(source);

        // Assertion
        actualResponse.assertEquals(expectedResponse);
    }

    @Test
    public void assertEquals_NotEqualsResponse_AssertionErrorTest() {

        AuthorizationResponseOutcome outcome = new AuthorizationResponseOutcome();
        outcome.setNetworkStatus("approved_by_network");
        outcome.setRiskLevel("normal");
        outcome.setRiskScore(64);
        outcome.setSellerMessage("Payment complete.");
        outcome.setType("authorized");

        AuthorizationResponseSource source = new AuthorizationResponseSource();
        source.setId("card_1DiD3z2eZvKYlo2CilH44yQY");
        source.setObject("card");
        source.setBrand("Visa");
        source.setCountry("US");
        source.setExpMonth(12);
        source.setExpYear(2019);
        source.setFingerprint("Xt5EWLLDS7FJjR1c");
        source.setFunding("credit");
        source.setLast4("4242");
        source.setMetadata(new AuthorizationResponseSourceMetadata());

        // Prepare expected response object
        AuthorizationResponse expectedResponse = new AuthorizationResponse();
        expectedResponse.setId("ch_1DiD402eZvKYlo2Cz8XicVgK");
        expectedResponse.setObject("charge");
        expectedResponse.setAmount(999f);
        expectedResponse.setCreated(1545018400);
        expectedResponse.setCurrency("usd");
        expectedResponse.setDescription("Successful charge");
        expectedResponse.setStatus("succeeded");

        expectedResponse.setOutcome(outcome);
        expectedResponse.setSource(source);

        // Prepare actual response object
        // Make sure two responses are different! ID will be changed!
        AuthorizationResponse actualResponse = new AuthorizationResponse();
        actualResponse.setId("Different ID");
        actualResponse.setObject(expectedResponse.getObject());
        actualResponse.setAmount(expectedResponse.getAmount());
        actualResponse.setCreated(expectedResponse.getCreated());
        actualResponse.setCurrency(expectedResponse.getCurrency());
        actualResponse.setDescription(expectedResponse.getDescription());
        actualResponse.setStatus(expectedResponse.getStatus());

        actualResponse.setOutcome(outcome);
        actualResponse.setSource(source);

        // Assertion
        try {
            actualResponse.assertEquals(expectedResponse);
        } catch (AssertionError error) {
            Assert.assertEquals(error.getMessage(), "expected [ch_1DiD402eZvKYlo2Cz8XicVgK] but found [Different ID]");
        }
    }
}
