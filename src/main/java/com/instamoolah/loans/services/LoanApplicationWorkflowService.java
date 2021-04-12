package com.instamoolah.loans.services;

import com.instamoolah.loans.controller.LoanPayload;
import com.instamoolah.loans.core.CollectionStatus;
import com.instamoolah.loans.core.LoanApplication;
import com.instamoolah.loans.core.LoanStatus;
import java.util.ArrayList;
import java.util.List;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanApplicationWorkflowService {

  static final String processDefinitionKey = "newInstamoolahLoanV2";

  @Autowired
  private RuntimeService runtimeService;

  public String startProcess(LoanPayload payload) {
    return runtimeService
      .createProcessInstanceBuilder()
      .processDefinitionKey(processDefinitionKey)
      .variable("riskScore", payload.riskScore)
      .variable("emailVerified", payload.emailVerified)
      .variable("collectionStatus", payload.collectionStatus)
      .variable("purpose", payload.purpose)
      .variable("amount", payload.amount)
      .start()
      .getId();
  }

  public List<LoanPayload> getProcesses() {
    List<ProcessInstance> activeProcesses = runtimeService
      .createProcessInstanceQuery()
      .active()
      .processDefinitionKey(processDefinitionKey)
      .list();

    List<LoanPayload> loans = new ArrayList<>(activeProcesses.size());
    activeProcesses.forEach(p -> loans.add(populator(p)));
    return loans;
  }

  public void deleteProcess(String id) {
    runtimeService.deleteProcessInstance(id, "customer delete");
  }

  private LoanPayload populator(ProcessInstance processInstance) {
    Integer riskScore = (Integer) runtimeService.getVariable(
      processInstance.getId(),
      "riskScore"
    );
    Integer amount = (Integer) runtimeService.getVariable(
      processInstance.getId(),
      "amount"
    );
    Boolean emailVerified = (Boolean) runtimeService.getVariable(
      processInstance.getId(),
      "emailVerified"
    );
    String collectionStatus = (String) runtimeService.getVariable(
      processInstance.getId(),
      "collectionStatus"
    );
    LoanStatus loanStatus = (LoanStatus) runtimeService.getVariable(
      processInstance.getId(),
      "loanStatus"
    );
    String purpose = (String) runtimeService.getVariable(
      processInstance.getId(),
      "purpose"
    );

    LoanPayload payload = new LoanPayload();
    payload.riskScore = riskScore;
    payload.emailVerified = emailVerified;
    payload.collectionStatus = collectionStatus;
    payload.status = loanStatus.name();
    payload.amount = amount;
    payload.purpose = purpose;
    payload.id = processInstance.getId();
    return payload;
  }
}
