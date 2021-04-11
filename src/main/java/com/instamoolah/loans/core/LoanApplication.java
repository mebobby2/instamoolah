package com.instamoolah.loans.core;

public class LoanApplication {

  private Integer riskScore;
  private Boolean emailVerified;
  private CollectionStatus collectionStatus;
  private LoanPurpose purpose;
  private Integer amount;
  private LoanStatus status;
  private String id;

  public LoanApplication(
    Integer riskScore,
    Boolean emailVerified,
    CollectionStatus collectionStatus
  ) {
    this.riskScore = riskScore;
    this.emailVerified = emailVerified;
    this.collectionStatus = collectionStatus;
    this.status = LoanStatus.NEW;
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

  public Integer getAmount() {
    return this.amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public LoanPurpose getPurpose() {
    return this.purpose;
  }

  public void setPurpose(LoanPurpose purpose) {
    this.purpose = purpose;
  }

  public LoanStatus getStatus() {
    return this.status;
  }

  public void setStatus(LoanStatus status) {
    this.status = status;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Boolean canAfford() {
    return (
      this.status == LoanStatus.AFFORDABILITY_PASSED ||
      this.status == LoanStatus.ACCEPTED ||
      this.status == LoanStatus.APPROVED
    );
  }
}
