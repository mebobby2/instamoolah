package com.instamoolah.loans.controller;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import com.instamoolah.loans.core.LoanApplication;
import com.instamoolah.loans.services.LoanApplicationWorkflowService;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class LoansController {

  private static final Logger LOGGER = LoggerFactory.getLogger(
    LoansController.class
  );

  @Autowired
  private LoanApplicationWorkflowService service;

  @PostMapping("/loans")
  public String start(@RequestBody LoanApplication application) {
    LOGGER.info("new loan application started");
    return service.startProcess(application);
  }

  @GetMapping("/loans")
  public List<LoanApplication> getLoans() {
    return service.getProcesses();
  }

  @DeleteMapping("/loans/{id}")
  public void deleteLoan(@PathVariable String id) {
    service.deleteProcess(id);
  }
}
