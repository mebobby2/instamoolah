package com.instamoolah.loans.delegate;

import com.instamoolah.loans.core.LoanApplication;
import com.instamoolah.loans.core.LoanPurpose;
import com.instamoolah.loans.core.LoanStatus;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("checkAutoApprove")
public class AutoApproveCheck implements JavaDelegate {

  private static final Logger LOGGER = LoggerFactory.getLogger(
    AutoApproveCheck.class
  );

  @Override
  public void execute(DelegateExecution execution) {
    Integer riskScore = execution.getVariable("riskScore", Integer.class);
    Integer amount = execution.getVariable("amount", Integer.class);
    Boolean emailVerified = execution.getVariable(
      "emailVerified",
      Boolean.class
    );
    String collectionStatus = execution.getVariable(
      "collectionStatus",
      String.class
    );
    String purpose = execution.getVariable(
      "purpose",
      String.class
    );
    String status = execution.getVariable(
      "loanStatus",
      String.class
    );

    LoanApplication application = new LoanApplication(
      riskScore,
      emailVerified,
      CollectionStatus.valueOf(collectionStatus)
    );
    application.setPurpose(LoanPurpose.valueOf(purpose));
    application.setAmount(amount);
    application.setStatus(LoanStatus.valueOf(status));

    // service.checkAffordability(application);

    if (application.isApproved()) {
      execution.setVariable("loanStatus", application.getStatus().name());
    }

    execution.setVariable("loanApproved", application.isApproved());
  }
}
