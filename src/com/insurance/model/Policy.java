package com.insurance.model;

public class Policy {
    private int policyId;
    private String policyNumber;
    private String type;
    private double coverageAmount;
    private double premiumAmount;

    // Getters and Setters

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    public double getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(double premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    @Override
    public String toString() {
        return "Policy ID: " + policyId +
                "\nPolicy Number: " + policyNumber +
                "\nType: " + type +
                "\nCoverage Amount: $" + coverageAmount +
                "\nPremium Amount: $" + premiumAmount;
    }
}
