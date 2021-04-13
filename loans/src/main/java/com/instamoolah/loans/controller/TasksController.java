package com.instamoolah.loans.controller;

import com.instamoolah.loans.services.LoanApplicationWorkflowService;
import java.util.List;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TasksController {

  private static final Logger LOGGER = LoggerFactory.getLogger(
    TasksController.class
  );

  @Autowired
  private LoanApplicationWorkflowService service;

  @GetMapping("/tasks/{processId}")
  public List<TaskPayload> getTasks(@PathVariable String processId) {
    LOGGER.info("Get credit officer tasks");
    return service.getTasks(processId);
  }

  @PutMapping("/tasks/creditofficers/approve/{taskId}")
  public void completeTask(@PathVariable String taskId) {
    LOGGER.info("Approve credit officer tasks");
    service.completeCreditOfficerTask(taskId);
  }

  @PutMapping("/tasks/creditofficers/reject/{taskId}")
  public void rejectTask(@PathVariable String taskId) {
    LOGGER.info("Reject credit officer tasks");
    service.rejectCreditOfficerTask(taskId);
  }

  @PutMapping("/tasks/customer/approve/{taskId}")
  public void completeCustomerTask(@PathVariable String taskId) {
    LOGGER.info("Approve customer tasks");
    service.approveCustomerTask(taskId);
  }

  @PutMapping("/tasks/customer/reject/{taskId}")
  public void rejectCustomerTask(@PathVariable String taskId) {
    LOGGER.info("Reject customer tasks");
    service.rejectCustomerTask(taskId);
  }
}
