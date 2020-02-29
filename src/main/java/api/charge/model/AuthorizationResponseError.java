package api.charge.model;

import org.testng.Assert;

public class AuthorizationResponseError {

    private String message;

    private String type;

    // Getters & Setters
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
     * Compare two objects, throw exception if any non-matched field
     * @param expectedError
     */
    public void assertEquals(AuthorizationResponseError expectedError) {

        Assert.assertEquals(this.getMessage(), expectedError.getMessage());
        Assert.assertEquals(this.getType(), expectedError.getType());
    }
}
