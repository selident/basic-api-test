package api.charge.model;

public class AuthorizationResponseOutcome {

    private String network_status;

    private String reason;

    private String risk_level;

    private long risk_score;

    private String seller_message;

    private String type;

    // Getters & Setters
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
}
