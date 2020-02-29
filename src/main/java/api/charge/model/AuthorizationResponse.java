package api.charge.model;

import java.util.Date;

public class AuthorizationResponse {

    private String id;

    private String object;

    private float amount;

    private long created;

    private String currency;

    private String customer;

    private String description;

    private String status;

    AuthorizationResponseOutcome outcome;

    AuthorizationResponseSource source;

    AuthorizationResponseError error;

    // Getters & Setters
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
        outcome = outcome;
    }

    public AuthorizationResponseSource getSource() {
        return source;
    }

    public void setSource(AuthorizationResponseSource source) {
        source = source;
    }

    public AuthorizationResponseError getError() {
        return error;
    }

    public void setError(AuthorizationResponseError error) {
        error = error;
    }
}
