package com.instamoolah.loans.services;

import com.instamoolah.loans.core.CollectionStatus;
import com.instamoolah.loans.core.LoanApplication;
import com.instamoolah.loans.core.LoanPurpose;
import com.instamoolah.loans.core.LoanStatus;

import java.util.ArrayList;
import java.util.List;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanApplicationWorkflowService {

  static final String processDefinitionKey = "newInstamoolahLoan";

  @Autowired
  private RuntimeService runtimeService;

  public String startProcess(LoanApplication app) {
    return runtimeService
      .createProcessInstanceBuilder()
      .processDefinitionKey(processDefinitionKey)
      .variable("riskScore", app.getRiskScore())
      .variable("emailVerified", app.getEmailVerified())
      .variable("collectionStatus", app.getCollectionStatus().name())
      .variable("purpose", app.getPurpose().name())
      .variable("amount", app.getAmount())
      .start()
      .getId();
  }

  public List<LoanApplication> getProcesses() {
    List<ProcessInstance> activeProcesses = runtimeService
      .createProcessInstanceQuery()
      .active()
      .includeProcessVariables()
      .processDefinitionKey(processDefinitionKey)
      .list();

    List<LoanApplication> loans = new ArrayList<>(activeProcesses.size());
    activeProcesses.forEach(p -> loans.add(populator(p)));
    return loans;
  }

  public void deleteProcess(String id) {
    runtimeService.deleteProcessInstance(id, "customer delete");
  }

  private LoanApplication populator(ProcessInstance processInstance) {
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
    String loanStatus = (String) runtimeService.getVariable(
      processInstance.getId(),
      "loanStatus"
    );
    String purpose = (String) runtimeService.getVariable(
      processInstance.getId(),
      "purpose"
    );

    LoanApplication loan = new LoanApplication(
      riskScore,
      emailVerified,
      CollectionStatus.valueOf(collectionStatus)
    );
    loan.setStatus(LoanStatus.valueOf(loanStatus));
    loan.setAmount(amount);
    loan.setPurpose(LoanPurpose.valueOf(purpose));
    loan.setId(processInstance.getId());
    return loan;
  }
}
