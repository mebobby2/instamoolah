package com.instamoolah.loans.controller;

import com.instamoolah.loans.core.LoanApplication;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class LoanController {

  private static final Logger LOGGER = LoggerFactory.getLogger(
    LoanController.class
  );
  private RuntimeService runtimeService;

  @Autowired
  public LoanController(RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
  }

  @PostMapping("/loan")
  public String start(@RequestBody LoanApplication application) {
    return runtimeService
      .createProcessInstanceBuilder()
      .processDefinitionKey("newInstamoolahLoan")
      .variable("riskScore", application.getRiskScore())
      .variable("emailVerified", application.getEmailVerified())
      .variable("collectionStatus", application.getCollectionStatus().name())
      .start()
      .getId();
  }
}
