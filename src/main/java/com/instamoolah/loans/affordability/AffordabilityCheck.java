package com.instamoolah.loans.affordability;

import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.flowable.engine.delegate.DelegateExecution;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

@Component("checkAffordability")
public class AffordabilityCheck implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) {
      // String employeeName = execution.getVariable("employeeName", String.class);
      // Integer numberOfDays = execution.getVariable("numberOfDays", Integer.class);
      // String vacationMotivation = execution.getVariable("vacationMotivation", String.class);

      // LOGGER.info("{} requested vacation for {} days. Motivation {}", employeeName, numberOfDays, vacationMotivation);

      execution.setVariable("affordabilityApproved", true);
  }

}
