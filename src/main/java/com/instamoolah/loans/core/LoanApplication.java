package com.instamoolah.loans.core;

public class LoanApplication {
    private Integer riskScore;
    private Boolean emailVerified;
    private CollectionStatus collectionStatus;

    public LoanApplication(Integer riskScore, Boolean emailVerified, CollectionStatus collectionStatus) {
      this.riskScore = riskScore;
      this.emailVerified = emailVerified;
      this.collectionStatus = collectionStatus;
    }

    public Integer getRiskScore() {
      return this.riskScore;
    }

    public void setRiskScore(Integer riskScore) {
      this.riskScore = riskScore;
    }

    public Boolean getEmailVerified() {
      return this.emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
      this.emailVerified = emailVerified;
    }

    public CollectionStatus getCollectionStatus() {
      return this.collectionStatus;
    }

    public void setCollectionStatus(CollectionStatus collectionStatus) {
      this.collectionStatus = collectionStatus;
    }
}
