package api.charge;

import api.charge.model.AuthorizationResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;

import java.util.HashMap;

public class AuthorizationServiceImpl implements AuthorizationService {

    private final static Logger logger = Logger.getLogger(AuthorizationServiceImpl.class);
    private String baseURL;

    public AuthorizationServiceImpl(String baseURL) {
        this.baseURL = baseURL;
    }

    @Override
    public Response getResponse(String creditCardNum, float amount, String currency) {

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
    public AuthorizationResponse normalizeResponse(Response response) {

        logger.info(response.asString());

        AuthorizationResponse authorizationResponse = response.as(AuthorizationResponse.class);
        logger.debug("authorization id =>> " + authorizationResponse.getId());

        return authorizationResponse;
    }
}
