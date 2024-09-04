package com.insurance.model;

import java.sql.Date;

public class Claim {
    private int claimId;
    private int policyId;
    private int customerId;
    private Date claimDate;
    private String status;

    // Getters and Setters

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Claim ID: " + claimId +
                "\nPolicy ID: " + policyId +
                "\nCustomer ID: " + customerId +
                "\nClaim Date: " + claimDate +
                "\nStatus: " + status;
    }
}
