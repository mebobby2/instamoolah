package com.instamoolah.loans.services;

import com.instamoolah.loans.core.CollectionStatus;
import com.instamoolah.loans.core.LoanApplication;
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
    Boolean emailVerified = (Boolean) runtimeService.getVariable(
      processInstance.getId(),
      "emailVerified"
    );
    String collectionStatus = (String) runtimeService.getVariable(
      processInstance.getId(),
      "collectionStatus"
    );

    LoanApplication loan = new LoanApplication(
      riskScore,
      emailVerified,
      CollectionStatus.valueOf(collectionStatus)
    );
    loan.setId(processInstance.getId());
    return loan;
  }
}
