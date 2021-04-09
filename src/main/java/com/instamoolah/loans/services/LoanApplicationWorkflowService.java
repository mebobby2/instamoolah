package com.instamoolah.loans.services;

import com.instamoolah.loans.core.LoanApplication;
import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanApplicationWorkflowService {

  @Autowired
  private RuntimeService runtimeService;

  public String startProcess(LoanApplication app) {
    return runtimeService
      .createProcessInstanceBuilder()
      .processDefinitionKey("newInstamoolahLoan")
      .variable("riskScore", app.getRiskScore())
      .variable("emailVerified", app.getEmailVerified())
      .variable("collectionStatus", app.getCollectionStatus().name())
      .start()
      .getId();
  }
}
