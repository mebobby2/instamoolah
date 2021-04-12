package com.instamoolah.loans.delegate;

import com.instamoolah.loans.core.LoanApplication;
import com.instamoolah.loans.core.LoanPurpose;
import com.instamoolah.loans.core.LoanStatus;
import com.instamoolah.loans.services.AutoApproveService;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("testDelegate")
public class TestDelegate implements JavaDelegate {

  private static final Logger LOGGER = LoggerFactory.getLogger(
    TestDelegate.class
  );

  @Override
  public void execute(DelegateExecution execution) {
    LOGGER.info("TestDelegateTestDelegateTestDelegateTestDelegateTestDelegate");
  }
}
