package com.instamoolah.loans.delegate;

import com.instamoolah.loans.core.LoanApplication;
import com.instamoolah.loans.core.LoanPurpose;
import com.instamoolah.loans.core.LoanStatus;
import com.instamoolah.loans.services.AutoApproveService;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("checkAutoApprove")
public class AutoApproveCheck implements JavaDelegate {

  @Autowired
  public AutoApproveService service;

  private static final Logger LOGGER = LoggerFactory.getLogger(
    AutoApproveCheck.class
  );

  @Override
  public void execute(DelegateExecution execution) {
    Integer amount = execution.getVariable("amount", Integer.class);
    String purpose = execution.getVariable("purpose", String.class);

    LoanApplication application = new LoanApplication(
      amount,
      LoanPurpose.valueOf(purpose)
    );

    LOGGER.info(
      "Checking for loan auto approval. Loan amount = {} and purpose = {}",
      amount,
      purpose
    );

    service.checkAutoApprove(application);

    LOGGER.info("Loan status is now {}", application.getStatus().name());

    // Uncomment the line below to stimulate a ArithmeticException to test flowable:async="true" feature
    // Integer result = 1/0;

    if (application.isApproved()) {
      execution.setVariable("loanStatus", application.getStatus());
    }
    execution.setVariable("loanApproved", application.isApproved());
  }
}
