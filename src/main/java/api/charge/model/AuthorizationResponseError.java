package api.charge.model;

import org.testng.Assert;

/**
 * Charge Authorization Response Error Object
 * The model to store values from JSON API response as a object - deserialize process
 */
public class AuthorizationResponseError {

    private String message;

    private String type;

    /**
     * Getters and Setters
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Compare this object with another one, throw exception if any non-matched field
     * @param expectedError expected error object
     */
    public void assertEquals(AuthorizationResponseError expectedError) {

        Assert.assertEquals(this.getMessage(), expectedError.getMessage());
        Assert.assertEquals(this.getType(), expectedError.getType());
    }
}
