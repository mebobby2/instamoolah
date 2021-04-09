package com.instamoolah.loans.affordability;

import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("checkAutoApprove")
public class AutoApproveCheck implements JavaDelegate {

  private static final Logger LOGGER = LoggerFactory.getLogger(AutoApproveCheck.class);

  @Override
  public void execute(DelegateExecution execution) {
      boolean affordabilityApproved = execution.getVariable("affordabilityApproved", Boolean.class);

      LOGGER.info("Affordability approval for loan is {}", affordabilityApproved);
  }
}
