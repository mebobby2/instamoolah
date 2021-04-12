package com.instamoolah.loans.services;

import com.instamoolah.loans.controller.LoanPayload;
import com.instamoolah.loans.core.CollectionStatus;
import com.instamoolah.loans.core.LoanApplication;
import com.instamoolah.loans.core.LoanStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.Map;
import java.util.stream.Collectors;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanApplicationWorkflowService {

  static final String processDefinitionKey = "newInstamoolahLoanV2";

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private HistoryService historyService;

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

    List<LoanPayload> oldLoans = getHistoricProcesses();

    return Stream
      .concat(loans.stream(), oldLoans.stream())
      .collect(Collectors.toList());
  }

  private List<LoanPayload> getHistoricProcesses() {
    List<HistoricProcessInstance> historyProcesses = historyService
      .createHistoricProcessInstanceQuery()
      .finished()
      .processDefinitionId(processDefinitionKey)
      .list();

    List<LoanPayload> loans = new ArrayList<>(historyProcesses.size());
    historyProcesses.forEach(p -> loans.add(populatorh(p)));
    return loans;
  }

  public void deleteProcess(String id) {
    runtimeService.deleteProcessInstance(id, "customer delete");
  }

  private LoanPayload populatorh(HistoricProcessInstance instance) {
    List<HistoricVariableInstance> variables = historyService
      .createHistoricVariableInstanceQuery()
      .processInstanceId(instance.getId())
      .list();

    Map<String, HistoricVariableInstance> map = variables
      .stream()
      .collect(
        Collectors.toMap(
          HistoricVariableInstance::getVariableName,
          item -> item
        )
      );

    LoanPayload payload = new LoanPayload();
    payload.riskScore = (Integer) map.get("riskScore").getValue();
    payload.emailVerified = (Boolean) map.get("emailVerified").getValue();
    payload.collectionStatus = (String) map.get("collectionStatus").getValue();
    payload.status = (String) map.get("collectionStatus").getValue();
    payload.amount = (Integer) map.get("amount").getValue();
    payload.purpose = (String) map.get("purpose").getValue();
    payload.id = instance.getId();
    return payload;
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
