package api.charge.model;

import org.testng.Assert;

/**
 * Charge Authorization Response Outcome Object
 * The model to store values from JSON API response as a object - deserialize process
 */
public class AuthorizationResponseOutcome {

    private String network_status;

    private String reason;

    private String risk_level;

    private long risk_score;

    private String seller_message;

    private String type;

    /**
     * Getters and Setters
     */
    public String getNetworkStatus() {
        return network_status;
    }

    public void setNetworkStatus(String networkStatus) {
        this.network_status = networkStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRiskLevel() {
        return risk_level;
    }

    public void setRiskLevel(String riskLevel) {
        this.risk_level = riskLevel;
    }

    public long getRiskScore() {
        return risk_score;
    }

    public void setRiskScore(long riskScore) {
        this.risk_score = riskScore;
    }

    public String getSellerMessage() {
        return seller_message;
    }

    public void setSellerMessage(String sellerMessage) {
        this.seller_message = sellerMessage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Compare this object with another one, throw exception if any non-matched field
     * @param expectedOutcome expected outcome object
     */
    public void assertEquals(AuthorizationResponseOutcome expectedOutcome) {

        Assert.assertEquals(this.getNetworkStatus(), expectedOutcome.getNetworkStatus());
        Assert.assertEquals(this.getReason(), expectedOutcome.getReason());
        Assert.assertEquals(this.getRiskLevel(), expectedOutcome.getRiskLevel());
        Assert.assertEquals(this.getRiskScore(), expectedOutcome.getRiskScore());
        Assert.assertEquals(this.getSellerMessage(), expectedOutcome.getSellerMessage());
        Assert.assertEquals(this.getType(), expectedOutcome.getType());
    }
}
