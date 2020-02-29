package api.charge;

import api.charge.model.AuthorizationResponse;
import io.restassured.response.Response;

/**
 * The Charge Authorization Service
 * Make API call to get response data
 * Normalize and deserialize JSON data into a model
 */
public interface AuthorizationService {

    public Response getResponse(String creditCardNum, float amount, String currency);

    public AuthorizationResponse normalizeResponse(Response response);
}
