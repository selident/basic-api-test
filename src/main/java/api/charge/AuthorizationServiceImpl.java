package api.charge;

import api.charge.model.AuthorizationResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

/**
 * Implementation of Charge Authorization Service
 */
public class AuthorizationServiceImpl implements AuthorizationService {

    private final static Logger logger = LogManager.getLogger(AuthorizationServiceImpl.class);

    private String baseURL;

    public AuthorizationServiceImpl(String baseURL) {
        this.baseURL = baseURL;
    }

    @Override
    public Response authorize(String creditCardNum, float amount, String currency) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", currency);

        Response response = RestAssured.given()
                .pathParam("credit_card_id", creditCardNum)
                .params(params)
                .get(this.baseURL);

        logger.debug(response.asString());

        return response;
    }

    @Override
    public AuthorizationResponse normalizeAuthorizeResponse(Response response) {

        logger.info(response.asString());

        AuthorizationResponse authorizationResponse = response.as(AuthorizationResponse.class);
        logger.debug("actual authorization id =>> " + authorizationResponse.getId());

        return authorizationResponse;
    }
}
