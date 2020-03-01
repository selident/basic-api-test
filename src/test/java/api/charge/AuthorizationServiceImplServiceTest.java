package api.charge;

import api.charge.model.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * Charge Authorization API Tests
 */
public class AuthorizationServiceImplServiceTest {

    private static final String BASE_URL = "http://5df9c4eee9f79e0014b6b2eb.mockapi.io/charge/{credit_card_id}";

    AuthorizationService authorizationService = new AuthorizationServiceImpl(BASE_URL);

    @Test
    public void authorize_InvalidCreditCardNumber_InvalidRequestErrorTest() {

        // Call API and get response
        Response response = authorizationService.authorize("40000007600000", 999f, "usd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeAuthorizeResponse(response);

        // Prepare expected data
        AuthorizationResponse expectedResponse = new AuthorizationResponse();
        expectedResponse.setAmount(0f);
        expectedResponse.setCreated(0);

        AuthorizationResponseError error = new AuthorizationResponseError();
        error.setMessage("Invalid Primary Account Number provided");
        error.setType("invalid_request_error");
        expectedResponse.setError(error);

        // Assertion
        expectedResponse.assertEquals(authorizationResponse);
    }

    @Test
    public void authorize_StolenCard_IssuerDeclinedTest() {

        // Call API and get response
        Response response = authorizationService.authorize("4000000000009979", 999f, "usd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeAuthorizeResponse(response);

        // Prepare expected data
        AuthorizationResponse expectedResponse = new AuthorizationResponse();
        expectedResponse.setId("ch_2DiD402eZvKYlo1Cz8XicVgK");
        expectedResponse.setAmount(0f);
        expectedResponse.setCreated(0);

        AuthorizationResponseOutcome outcome = new AuthorizationResponseOutcome();
        outcome.setNetworkStatus("declined_by_network");
        outcome.setReason("stolen_card");
        outcome.setType("issuer_declined");
        expectedResponse.setOutcome(outcome);

        // Assertion
        expectedResponse.assertEquals(authorizationResponse);
    }

    @Test
    public void authorize_InsufficientFunds_IssuerDeclinedTest() {

        // Call API and get response
        Response response = authorizationService.authorize("4000000000009995", 999f, "usd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeAuthorizeResponse(response);

        // Prepare expected data
        AuthorizationResponse expectedResponse = new AuthorizationResponse();
        expectedResponse.setId("ch_1DiD402eZvKYlo2Cz8XicVgK");
        expectedResponse.setAmount(0f);
        expectedResponse.setCreated(0);

        AuthorizationResponseOutcome outcome = new AuthorizationResponseOutcome();
        outcome.setNetworkStatus("declined_by_network");
        outcome.setReason("insufficient_funds");
        outcome.setType("issuer_declined");
        expectedResponse.setOutcome(outcome);

        // Assertion
        expectedResponse.assertEquals(authorizationResponse);
    }

    @Test
    public void authorize_LostCard_IssuerDeclinedTest() {

        // Call API and get response
        Response response = authorizationService.authorize("4000000000009987", 999f, "usd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeAuthorizeResponse(response);

        // Prepare expected data
        AuthorizationResponse expectedResponse = new AuthorizationResponse();
        expectedResponse.setId("ch_1DiD402qZ2KYlo2Cz8XicVgK");
        expectedResponse.setAmount(0f);
        expectedResponse.setCreated(0);

        AuthorizationResponseOutcome outcome = new AuthorizationResponseOutcome();
        outcome.setNetworkStatus("declined_by_network");
        outcome.setReason("lost_card");
        outcome.setType("issuer_declined");
        expectedResponse.setOutcome(outcome);

        // Assertion
        expectedResponse.assertEquals(authorizationResponse);
    }

    @Test
    public void authorize_Fraudulent_BlockedTest() {

        // Call API and get response
        Response response = authorizationService.authorize("4100000000000019", 999f, "usd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeAuthorizeResponse(response);

        // Prepare expected data
        AuthorizationResponse expectedResponse = new AuthorizationResponse();
        expectedResponse.setId("ch_1DiD402eZvKY123Cz8XicVgK");
        expectedResponse.setObject("charge");
        expectedResponse.setAmount(999f);
        expectedResponse.setCreated(1545018400);
        expectedResponse.setCurrency("usd");
        expectedResponse.setDescription("Blocked charge");
        expectedResponse.setStatus("blocked");

        AuthorizationResponseOutcome outcome = new AuthorizationResponseOutcome();
        outcome.setNetworkStatus("not_sent_to_network");
        outcome.setReason("highest_risk_level");
        outcome.setType("blocked");
        expectedResponse.setOutcome(outcome);

        // Assertion
        expectedResponse.assertEquals(authorizationResponse);

        // This is additional assertion. I created a method getCreatedCreatedDate so that we can get the data with Date type.
        // In the real case, the created time will be changed for every API call, the way we assert in that case will be different.
        Assert.assertEquals(authorizationResponse.getCreatedDate(), new Date(1545018400));
    }

    @Test
    public void authorize_CreatedSuccessfully_AuthorizedTest() {

        // Call API and get response
        Response response = authorizationService.authorize("4242424242424242", 999f, "usd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeAuthorizeResponse(response);

        // Prepare expected data
        AuthorizationResponse expectedResponse = new AuthorizationResponse();
        expectedResponse.setId("ch_1DiD402eZvKYlo2Cz8XicVgK");
        expectedResponse.setObject("charge");
        expectedResponse.setAmount(999f);
        expectedResponse.setCreated(1545018400);
        expectedResponse.setCurrency("usd");
        expectedResponse.setDescription("Successful charge");
        expectedResponse.setStatus("succeeded");

        AuthorizationResponseOutcome outcome = new AuthorizationResponseOutcome();
        outcome.setNetworkStatus("approved_by_network");
        outcome.setRiskLevel("normal");
        outcome.setRiskScore(64);
        outcome.setSellerMessage("Payment complete.");
        outcome.setType("authorized");
        expectedResponse.setOutcome(outcome);

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
        expectedResponse.setSource(source);

        // Assertion
        expectedResponse.assertEquals(authorizationResponse);
    }

    /**
     * Additional test which were not listed in the exercise.
     * 1234567890 is not a valid credit card, the expectation for this test is the same with invalid credit card test!
     */
    //@Test
    public void authorize_NotFoundCardNumber_InvalidRequestErrorTest() {

        // Call API and get response
        Response response = authorizationService.authorize("1234567890", 999f, "usd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeAuthorizeResponse(response);

        // Prepare expected data
        AuthorizationResponse expectedResponse = new AuthorizationResponse();
        expectedResponse.setAmount(0f);
        expectedResponse.setCreated(0);

        AuthorizationResponseError error = new AuthorizationResponseError();
        error.setMessage("Invalid Primary Account Number provided");
        error.setType("invalid_request_error");
        expectedResponse.setError(error);

        // Assertion
        expectedResponse.assertEquals(authorizationResponse);
    }

    /**
     * The amount number had been changed to 2500f, then the response should show that number!
     * We can extend this test by sending really big big or negative numbers! but it seems the API only returned fixed values.
     */
    //@Test
    public void authorize_ChangeAmount_ReturnCorrespondingAmountTest() {

        // Call API and get response
        Response response = authorizationService.authorize("4242424242424242", 2500f, "usd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeAuthorizeResponse(response);

        // Prepare expected data
        AuthorizationResponse expectedResponse = new AuthorizationResponse();
        expectedResponse.setAmount(2500f);

        expectedResponse.setId("ch_1DiD402eZvKYlo2Cz8XicVgK");
        expectedResponse.setObject("charge");
        expectedResponse.setCreated(1545018400);
        expectedResponse.setCurrency("usd");
        expectedResponse.setDescription("Successful charge");
        expectedResponse.setStatus("succeeded");

        AuthorizationResponseOutcome outcome = new AuthorizationResponseOutcome();
        outcome.setNetworkStatus("approved_by_network");
        outcome.setRiskLevel("normal");
        outcome.setRiskScore(64);
        outcome.setSellerMessage("Payment complete.");
        outcome.setType("authorized");
        expectedResponse.setOutcome(outcome);

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
        expectedResponse.setSource(source);

        // Assertion
        expectedResponse.assertEquals(authorizationResponse);
    }

    /**
     * Beside amount 2500f, the currency had been changed to vnd, then the response should show those values!
     * We can extend this test by sending invalid currencies! but it seems the API only returned fixed values.
     */
    //@Test
    public void authorize_ChangeCurrency_ReturnCorrespondingCurrencyTest() {

        // Call API and get response
        Response response = authorizationService.authorize("4242424242424242", 2500f, "vnd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeAuthorizeResponse(response);

        // Prepare expected data
        AuthorizationResponse expectedResponse = new AuthorizationResponse();
        expectedResponse.setAmount(2500f);
        expectedResponse.setCurrency("vnd");

        expectedResponse.setId("ch_1DiD402eZvKYlo2Cz8XicVgK");
        expectedResponse.setObject("charge");
        expectedResponse.setCreated(1545018400);
        expectedResponse.setDescription("Successful charge");
        expectedResponse.setStatus("succeeded");

        AuthorizationResponseOutcome outcome = new AuthorizationResponseOutcome();
        outcome.setNetworkStatus("approved_by_network");
        outcome.setRiskLevel("normal");
        outcome.setRiskScore(64);
        outcome.setSellerMessage("Payment complete.");
        outcome.setType("authorized");
        expectedResponse.setOutcome(outcome);

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
        expectedResponse.setSource(source);

        // Assertion
        expectedResponse.assertEquals(authorizationResponse);
    }
}
