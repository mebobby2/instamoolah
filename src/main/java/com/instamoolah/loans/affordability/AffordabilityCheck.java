package com.instamoolah.loans.affordability;

import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("checkAffordability")
public class AffordabilityCheck implements JavaDelegate {

  private static final Logger LOGGER = LoggerFactory.getLogger(AffordabilityCheck.class);

  @Override
  public void execute(DelegateExecution execution) {
      int harmoneyScore = execution.getVariable("harmoneyScore", Integer.class);
      boolean emailVerified = execution.getVariable("emailVerified", Boolean.class);
      String collectionStatus = execution.getVariable("collectionStatus", String.class);

      LOGGER.info("Loan requested with harmoney score = {}, email verified = {}, and collection status = {}", harmoneyScore, emailVerified, collectionStatus);

      // Run Drools engine here

      execution.setVariable("affordabilityApproved", true);
  }
}
