package com.instamoolah.loans.delegate;

import com.instamoolah.loans.core.Applicant;
import com.instamoolah.loans.core.CollectionStatus;
import com.instamoolah.loans.core.LoanStatus;
import com.instamoolah.loans.services.AffordabilityService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("checkAffordability")
public class AffordabilityCheck implements JavaDelegate {

  @Autowired
  public AffordabilityService service;

  private static final Logger LOGGER = LoggerFactory.getLogger(
    AffordabilityCheck.class
  );

  @Override
  public void execute(DelegateExecution execution) {
    Integer riskScore = execution.getVariable("riskScore", Integer.class);
    Boolean emailVerified = execution.getVariable(
      "emailVerified",
      Boolean.class
    );
    String collectionStatus = execution.getVariable(
      "collectionStatus",
      String.class
    );

    LOGGER.info(
      "Loan requested with risk score = {}, email verified = {}, and collection status = {}",
      riskScore,
      emailVerified,
      collectionStatus
    );

    Applicant applicant = new Applicant(
      riskScore,
      emailVerified,
      CollectionStatus.valueOf(collectionStatus)
    );
    service.checkAffordability(applicant);

    execution.setVariable(
      "affordabilityApproved",
      applicant.getAffordabilityApproved()
    );
    execution.setVariable(
      "loanStatus",
      LoanStatus.getStatusFromAffordabilityStatus(
        applicant.getAffordabilityApproved()
      )
    );
  }
}
