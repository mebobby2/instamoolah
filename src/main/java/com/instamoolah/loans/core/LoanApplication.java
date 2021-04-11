package com.instamoolah.loans.core;

public class LoanApplication {

  private Applicant applicant;
  private LoanPurpose purpose;
  private Integer amount;
  private LoanStatus status;
  private String id;

  public LoanApplication(
    Integer amount,
    LoanPurpose purpose
  ) {
    this.amount = amount;
    this.purpose = purpose;
    this.status = LoanStatus.NEW;
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

  public void setApplicant(Applicant applicant) {
    this.applicant = applicant;
  }

  public Applicant getApplicant() {
    return this.applicant;
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

  public Boolean isApproved() {
    return (this.status == LoanStatus.APPROVED);
  }
}
