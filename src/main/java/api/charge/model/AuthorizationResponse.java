package api.charge.model;

import org.testng.Assert;

import java.util.Date;

/**
 * Charge Authorization Response Object
 * The model to store values from JSON API response as a object - deserialize process
 */
public class AuthorizationResponse {

    private String id;

    private String object;

    private float amount;

    private long created;

    private String currency;

    private String customer;

    private String description;

    private String status;

    private AuthorizationResponseOutcome outcome;

    private AuthorizationResponseSource source;

    private AuthorizationResponseError error;

    /**
     * Getters and Setters
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getCreatedDate() {

        return new Date(created);
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AuthorizationResponseOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(AuthorizationResponseOutcome outcome) {
        this.outcome = outcome;
    }

    public AuthorizationResponseSource getSource() {
        return source;
    }

    public void setSource(AuthorizationResponseSource source) {
        this.source = source;
    }

    public AuthorizationResponseError getError() {
        return error;
    }

    public void setError(AuthorizationResponseError error) {
        this.error = error;
    }

    /**
     * Compare this object with another one, throw exception if any non-matched field
     * @param expectedResponse expected data object
     */
    public void assertEquals(AuthorizationResponse expectedResponse) {

        Assert.assertEquals(this.getId(), expectedResponse.getId());
        Assert.assertEquals(this.getObject(), expectedResponse.getObject());
        Assert.assertEquals(this.getAmount(), expectedResponse.getAmount());
        Assert.assertEquals(this.getCreated(), expectedResponse.getCreated());
        Assert.assertEquals(this.getCurrency(), expectedResponse.getCurrency());
        Assert.assertEquals(this.getCustomer(), expectedResponse.getCustomer());
        Assert.assertEquals(this.getDescription(), expectedResponse.getDescription());
        Assert.assertEquals(this.getStatus(), expectedResponse.getStatus());

        if (this.getOutcome() != null) {
            this.getOutcome().assertEquals(expectedResponse.getOutcome());
        } else {
            Assert.assertNull(expectedResponse.getOutcome());
        }

        if (this.getSource() != null) {
            this.getSource().assertEquals(expectedResponse.getSource());
        } else {
            Assert.assertNull(expectedResponse.getSource());
        }

        if (this.getError() != null) {
            this.getError().assertEquals(expectedResponse.getError());
        } else {
            Assert.assertNull(expectedResponse.getError());
        }
    }
}
