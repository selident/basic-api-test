package api.charge;

import api.charge.model.AuthorizationResponse;
import io.restassured.response.Response;

public interface AuthorizationService {

    public Response getResponse(String creditCardNum, float amount, String currency);

    public AuthorizationResponse normalizeResponse(Response response);
}
