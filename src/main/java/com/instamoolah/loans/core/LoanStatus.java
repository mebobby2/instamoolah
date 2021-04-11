package com.instamoolah.loans.core;

public enum LoanStatus {
  NEW,
  AFFORDABILITY_PASSED,
  APPROVED,
  ACCEPTED,
  REJECTED;

  public static LoanStatus getStatusFromAffordabilityStatus(Boolean affordabilityApproved) {
    return affordabilityApproved ? LoanStatus.AFFORDABILITY_PASSED : LoanStatus.REJECTED;
  }
}
