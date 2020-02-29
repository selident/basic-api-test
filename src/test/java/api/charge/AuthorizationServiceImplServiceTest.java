package api.charge;

import api.charge.model.AuthorizationResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

public class AuthorizationServiceImplServiceTest {

    private static final String BASE_URL = "http://5df9c4eee9f79e0014b6b2eb.mockapi.io/charge/{credit_card_id}";
    AuthorizationService authorizationService = new AuthorizationServiceImpl(BASE_URL);

    @Test
    public void chargeAuthorizationInvalidCreditCardNumberTest() {

        // Call API and get response
        Response response = authorizationService.getResponse("40000007600000", 999f, "usd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeResponse(response);

        // Assert response values
        Assert.assertNull(authorizationResponse.getId());
        Assert.assertNull(authorizationResponse.getObject());
        Assert.assertEquals(authorizationResponse.getAmount(), 0f);
        Assert.assertEquals(authorizationResponse.getCreated(), 0);
        Assert.assertNull(authorizationResponse.getCurrency());
        Assert.assertNull(authorizationResponse.getCustomer());
        Assert.assertNull(authorizationResponse.getDescription());
        Assert.assertNull(authorizationResponse.getStatus());

        Assert.assertNull(authorizationResponse.getOutcome());
        Assert.assertNull(authorizationResponse.getSource());

        Assert.assertEquals(authorizationResponse.getError().getMessage(), "Invalid Primary Account Number provided");
        Assert.assertEquals(authorizationResponse.getError().getType(), "invalid_request_error");
    }

    @Test
    public void chargeAuthorizationStolenCardTest() {

        // Call API and get response
        Response response = authorizationService.getResponse("4000000000009979", 999f, "usd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeResponse(response);

        // Assert response values
        Assert.assertEquals(authorizationResponse.getId(), "ch_2DiD402eZvKYlo1Cz8XicVgK");
        Assert.assertNull(authorizationResponse.getObject());
        Assert.assertEquals(authorizationResponse.getAmount(), 0f);
        Assert.assertEquals(authorizationResponse.getCreated(), 0);
        Assert.assertNull(authorizationResponse.getCurrency());
        Assert.assertNull(authorizationResponse.getCustomer());
        Assert.assertNull(authorizationResponse.getDescription());
        Assert.assertNull(authorizationResponse.getStatus());

        Assert.assertEquals(authorizationResponse.getOutcome().getNetworkStatus(), "declined_by_network");
        Assert.assertEquals(authorizationResponse.getOutcome().getReason(), "stolen_card");
        Assert.assertEquals(authorizationResponse.getOutcome().getType(), "issuer_declined");

        Assert.assertNull(authorizationResponse.getSource());
        Assert.assertNull(authorizationResponse.getError());
    }

    @Test
    public void chargeAuthorizationInsufficientFundsTest() {

        // Call API and get response
        Response response = authorizationService.getResponse("4000000000009995", 999f, "usd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeResponse(response);

        // Assert response values
        Assert.assertEquals(authorizationResponse.getId(), "ch_1DiD402eZvKYlo2Cz8XicVgK");
        Assert.assertNull(authorizationResponse.getObject());
        Assert.assertEquals(authorizationResponse.getAmount(), 0f);
        Assert.assertEquals(authorizationResponse.getCreated(), 0);
        Assert.assertNull(authorizationResponse.getCurrency());
        Assert.assertNull(authorizationResponse.getCustomer());
        Assert.assertNull(authorizationResponse.getDescription());
        Assert.assertNull(authorizationResponse.getStatus());

        Assert.assertEquals(authorizationResponse.getOutcome().getNetworkStatus(), "declined_by_network");
        Assert.assertEquals(authorizationResponse.getOutcome().getReason(), "insufficient_funds");
        Assert.assertEquals(authorizationResponse.getOutcome().getType(), "issuer_declined");

        Assert.assertNull(authorizationResponse.getSource());
        Assert.assertNull(authorizationResponse.getError());
    }

    @Test
    public void chargeAuthorizationLostCardTest() {

        // Call API and get response
        Response response = authorizationService.getResponse("4000000000009987", 999f, "usd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeResponse(response);

        // Assert response values
        Assert.assertEquals(authorizationResponse.getId(), "ch_1DiD402qZ2KYlo2Cz8XicVgK");
        Assert.assertNull(authorizationResponse.getObject());
        Assert.assertEquals(authorizationResponse.getAmount(), 0f);
        Assert.assertEquals(authorizationResponse.getCreated(), 0);
        Assert.assertNull(authorizationResponse.getCurrency());
        Assert.assertNull(authorizationResponse.getCustomer());
        Assert.assertNull(authorizationResponse.getDescription());
        Assert.assertNull(authorizationResponse.getStatus());

        Assert.assertEquals(authorizationResponse.getOutcome().getNetworkStatus(), "declined_by_network");
        Assert.assertEquals(authorizationResponse.getOutcome().getReason(), "lost_card");
        Assert.assertEquals(authorizationResponse.getOutcome().getType(), "issuer_declined");

        Assert.assertNull(authorizationResponse.getSource());
        Assert.assertNull(authorizationResponse.getError());
    }

    @Test
    public void chargeAuthorizationFraudulentBlockedTest() {

        // Call API and get response
        Response response = authorizationService.getResponse("4100000000000019", 999f, "usd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeResponse(response);

        // Assert response values
        Assert.assertEquals(authorizationResponse.getId(), "ch_1DiD402eZvKY123Cz8XicVgK");
        Assert.assertEquals(authorizationResponse.getObject(), "charge");
        Assert.assertEquals(authorizationResponse.getAmount(), 999f);
        Assert.assertEquals(authorizationResponse.getCreated(), 1545018400);
        Assert.assertEquals(authorizationResponse.getCurrency(), "usd");
        Assert.assertNull(authorizationResponse.getCustomer());
        Assert.assertEquals(authorizationResponse.getDescription(), "Blocked charge");
        Assert.assertEquals(authorizationResponse.getStatus(), "blocked");

        Assert.assertEquals(authorizationResponse.getOutcome().getNetworkStatus(), "not_sent_to_network");
        Assert.assertEquals(authorizationResponse.getOutcome().getReason(), "highest_risk_level");
        Assert.assertEquals(authorizationResponse.getOutcome().getType(), "blocked");

        Assert.assertNull(authorizationResponse.getSource());
        Assert.assertNull(authorizationResponse.getError());

        // This is additional assertion. I created a method getCreatedCreatedDate so that we can get the data with Date type.
        // In the real case, the created time will be changed for every API call, the way we assert in that case will be different.
        Assert.assertEquals(authorizationResponse.getCreatedDate(), new Date(1545018400));
    }

    @Test
    public void chargeAuthorizationCreatedSuccessfullyTest() {

        // Call API and get response
        Response response = authorizationService.getResponse("4242424242424242", 999f, "usd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeResponse(response);

        // Assert response values
        Assert.assertEquals(authorizationResponse.getId(), "ch_1DiD402eZvKYlo2Cz8XicVgK");
        Assert.assertEquals(authorizationResponse.getObject(), "charge");
        Assert.assertEquals(authorizationResponse.getAmount(), 999f);
        Assert.assertEquals(authorizationResponse.getCreated(), 1545018400);
        Assert.assertEquals(authorizationResponse.getCurrency(), "usd");
        Assert.assertNull(authorizationResponse.getCustomer());
        Assert.assertEquals(authorizationResponse.getDescription(), "Successful charge");
        Assert.assertEquals(authorizationResponse.getStatus(), "succeeded");

        Assert.assertEquals(authorizationResponse.getOutcome().getNetworkStatus(), "approved_by_network");
        Assert.assertNull(authorizationResponse.getOutcome().getReason());
        Assert.assertEquals(authorizationResponse.getOutcome().getRiskLevel(), "normal");
        Assert.assertEquals(authorizationResponse.getOutcome().getRiskScore(), 64);
        Assert.assertEquals(authorizationResponse.getOutcome().getSellerMessage(), "Payment complete.");
        Assert.assertEquals(authorizationResponse.getOutcome().getType(), "authorized");

        Assert.assertEquals(authorizationResponse.getSource().getId(), "card_1DiD3z2eZvKYlo2CilH44yQY");
        Assert.assertEquals(authorizationResponse.getSource().getObject(), "card");
        Assert.assertNull(authorizationResponse.getSource().getAddressCity());
        Assert.assertNull(authorizationResponse.getSource().getAddressCountry());
        Assert.assertNull(authorizationResponse.getSource().getAddressLine1());
        Assert.assertNull(authorizationResponse.getSource().getAddressLine1Check());
        Assert.assertNull(authorizationResponse.getSource().getAddressLine2());
        Assert.assertNull(authorizationResponse.getSource().getAddressState());
        Assert.assertNull(authorizationResponse.getSource().getAddressZip());
        Assert.assertNull(authorizationResponse.getSource().getAddressZipCheck());

        Assert.assertEquals(authorizationResponse.getSource().getBrand(), "Visa");
        Assert.assertEquals(authorizationResponse.getSource().getCountry(), "US");
        Assert.assertNull(authorizationResponse.getSource().getCustomer());
        Assert.assertNull(authorizationResponse.getSource().getCvcCheck());
        Assert.assertNull(authorizationResponse.getSource().getDynamicLast4());

        Assert.assertEquals(authorizationResponse.getSource().getExpMonth(), 12);
        Assert.assertEquals(authorizationResponse.getSource().getExpYear(), 2019);
        Assert.assertEquals(authorizationResponse.getSource().getFingerPrint(), "Xt5EWLLDS7FJjR1c");
        Assert.assertEquals(authorizationResponse.getSource().getFunding(), "credit");
        Assert.assertEquals(authorizationResponse.getSource().getLast4(), "4242");

        // Metadata appeared in the JSON response, then it is not null object, just an empty node!
        Assert.assertNotNull(authorizationResponse.getSource().getMetadata());

        Assert.assertNull(authorizationResponse.getSource().getName());
        Assert.assertNull(authorizationResponse.getSource().getTokenizationMethod());
        Assert.assertNull(authorizationResponse.getError());
    }


    /**
     * Additional test which were not listed in the exercise.
     * 1234567890 is not a valid credit card, the expectation for this test is the same with invalid credit card!
     */
    @Test
    public void chargeAuthorizationOutOfTheBox_NotFoundCardNumTest() {

        // Call API and get response
        Response response = authorizationService.getResponse("1234567890", 999f, "usd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeResponse(response);

        // Assert response values
        Assert.assertNull(authorizationResponse.getId());
        Assert.assertNull(authorizationResponse.getObject());
        Assert.assertEquals(authorizationResponse.getAmount(), 0f);
        Assert.assertEquals(authorizationResponse.getCreated(), 0);
        Assert.assertNull(authorizationResponse.getCurrency());
        Assert.assertNull(authorizationResponse.getCustomer());
        Assert.assertNull(authorizationResponse.getDescription());
        Assert.assertNull(authorizationResponse.getStatus());

        Assert.assertNull(authorizationResponse.getOutcome());
        Assert.assertNull(authorizationResponse.getSource());

        Assert.assertEquals(authorizationResponse.getError().getMessage(), "Invalid Primary Account Number provided");
        Assert.assertEquals(authorizationResponse.getError().getType(), "invalid_request_error");
    }

    /**
     * The amount number had been changed to 2500f, then the response should show that number!
     * We can extend this test by sending really big big or negative numbers! but it seems the API is mock and only returned fixed values.
     */
    @Test
    public void chargeAuthorizationOutOfTheBox_ChangingAmountTest() {

        // Call API and get response
        Response response = authorizationService.getResponse("4242424242424242", 2500f, "usd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeResponse(response);

        // Assert response values
        Assert.assertEquals(authorizationResponse.getAmount(), 2500f);

        Assert.assertEquals(authorizationResponse.getId(), "ch_1DiD402eZvKYlo2Cz8XicVgK");
        Assert.assertEquals(authorizationResponse.getObject(), "charge");
        Assert.assertEquals(authorizationResponse.getCreated(), 1545018400);
        Assert.assertEquals(authorizationResponse.getCurrency(), "usd");
        Assert.assertNull(authorizationResponse.getCustomer());
        Assert.assertEquals(authorizationResponse.getDescription(), "Successful charge");
        Assert.assertEquals(authorizationResponse.getStatus(), "succeeded");

        Assert.assertEquals(authorizationResponse.getOutcome().getNetworkStatus(), "approved_by_network");
        Assert.assertNull(authorizationResponse.getOutcome().getReason());
        Assert.assertEquals(authorizationResponse.getOutcome().getRiskLevel(), "normal");
        Assert.assertEquals(authorizationResponse.getOutcome().getRiskScore(), 64);
        Assert.assertEquals(authorizationResponse.getOutcome().getSellerMessage(), "Payment complete.");
        Assert.assertEquals(authorizationResponse.getOutcome().getType(), "authorized");

        Assert.assertEquals(authorizationResponse.getSource().getId(), "card_1DiD3z2eZvKYlo2CilH44yQY");
        Assert.assertEquals(authorizationResponse.getSource().getObject(), "card");
        Assert.assertNull(authorizationResponse.getSource().getAddressCity());
        Assert.assertNull(authorizationResponse.getSource().getAddressCountry());
        Assert.assertNull(authorizationResponse.getSource().getAddressLine1());
        Assert.assertNull(authorizationResponse.getSource().getAddressLine1Check());
        Assert.assertNull(authorizationResponse.getSource().getAddressLine2());
        Assert.assertNull(authorizationResponse.getSource().getAddressState());
        Assert.assertNull(authorizationResponse.getSource().getAddressZip());
        Assert.assertNull(authorizationResponse.getSource().getAddressZipCheck());

        Assert.assertEquals(authorizationResponse.getSource().getBrand(), "Visa");
        Assert.assertEquals(authorizationResponse.getSource().getCountry(), "US");
        Assert.assertNull(authorizationResponse.getSource().getCustomer());
        Assert.assertNull(authorizationResponse.getSource().getCvcCheck());
        Assert.assertNull(authorizationResponse.getSource().getDynamicLast4());

        Assert.assertEquals(authorizationResponse.getSource().getExpMonth(), 12);
        Assert.assertEquals(authorizationResponse.getSource().getExpYear(), 2019);
        Assert.assertEquals(authorizationResponse.getSource().getFingerPrint(), "Xt5EWLLDS7FJjR1c");
        Assert.assertEquals(authorizationResponse.getSource().getFunding(), "credit");
        Assert.assertEquals(authorizationResponse.getSource().getLast4(), "4242");
        Assert.assertNotNull(authorizationResponse.getSource().getMetadata());

        Assert.assertNull(authorizationResponse.getSource().getName());
        Assert.assertNull(authorizationResponse.getSource().getTokenizationMethod());
        Assert.assertNull(authorizationResponse.getError());
    }

    /**
     * Beside amount 2500f, the currency had been changed to vnd, then the response should show those values!
     * We can extend this test by sending invalid currencies! but it seems the API is mock and only returned fixed values.
     */
    @Test
    public void chargeAuthorizationOutOfTheBox_ChangingCurrencyTest() {

        // Call API and get response
        Response response = authorizationService.getResponse("4242424242424242", 2500f, "vnd");
        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        // Deserialize JSON response
        AuthorizationResponse authorizationResponse = authorizationService.normalizeResponse(response);

        // Assert response values
        Assert.assertEquals(authorizationResponse.getAmount(), 2500f);
        Assert.assertEquals(authorizationResponse.getCurrency(), "vnd");

        Assert.assertEquals(authorizationResponse.getId(), "ch_1DiD402eZvKYlo2Cz8XicVgK");
        Assert.assertEquals(authorizationResponse.getObject(), "charge");
        Assert.assertEquals(authorizationResponse.getCreated(), 1545018400);
        Assert.assertNull(authorizationResponse.getCustomer());
        Assert.assertEquals(authorizationResponse.getDescription(), "Successful charge");
        Assert.assertEquals(authorizationResponse.getStatus(), "succeeded");

        Assert.assertEquals(authorizationResponse.getOutcome().getNetworkStatus(), "approved_by_network");
        Assert.assertNull(authorizationResponse.getOutcome().getReason());
        Assert.assertEquals(authorizationResponse.getOutcome().getRiskLevel(), "normal");
        Assert.assertEquals(authorizationResponse.getOutcome().getRiskScore(), 64);
        Assert.assertEquals(authorizationResponse.getOutcome().getSellerMessage(), "Payment complete.");
        Assert.assertEquals(authorizationResponse.getOutcome().getType(), "authorized");

        Assert.assertEquals(authorizationResponse.getSource().getId(), "card_1DiD3z2eZvKYlo2CilH44yQY");
        Assert.assertEquals(authorizationResponse.getSource().getObject(), "card");
        Assert.assertNull(authorizationResponse.getSource().getAddressCity());
        Assert.assertNull(authorizationResponse.getSource().getAddressCountry());
        Assert.assertNull(authorizationResponse.getSource().getAddressLine1());
        Assert.assertNull(authorizationResponse.getSource().getAddressLine1Check());
        Assert.assertNull(authorizationResponse.getSource().getAddressLine2());
        Assert.assertNull(authorizationResponse.getSource().getAddressState());
        Assert.assertNull(authorizationResponse.getSource().getAddressZip());
        Assert.assertNull(authorizationResponse.getSource().getAddressZipCheck());

        Assert.assertEquals(authorizationResponse.getSource().getBrand(), "Visa");
        Assert.assertEquals(authorizationResponse.getSource().getCountry(), "US");
        Assert.assertNull(authorizationResponse.getSource().getCustomer());
        Assert.assertNull(authorizationResponse.getSource().getCvcCheck());
        Assert.assertNull(authorizationResponse.getSource().getDynamicLast4());

        Assert.assertEquals(authorizationResponse.getSource().getExpMonth(), 12);
        Assert.assertEquals(authorizationResponse.getSource().getExpYear(), 2019);
        Assert.assertEquals(authorizationResponse.getSource().getFingerPrint(), "Xt5EWLLDS7FJjR1c");
        Assert.assertEquals(authorizationResponse.getSource().getFunding(), "credit");
        Assert.assertEquals(authorizationResponse.getSource().getLast4(), "4242");
        Assert.assertNotNull(authorizationResponse.getSource().getMetadata());

        Assert.assertNull(authorizationResponse.getSource().getName());
        Assert.assertNull(authorizationResponse.getSource().getTokenizationMethod());
        Assert.assertNull(authorizationResponse.getError());
    }
}
