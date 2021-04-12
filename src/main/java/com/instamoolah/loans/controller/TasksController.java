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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TasksController {

  private static final Logger LOGGER = LoggerFactory.getLogger(
    TasksController.class
  );

  @Autowired
  private LoanApplicationWorkflowService service;

  @GetMapping("/tasks/creditofficers")
  public List<TaskPayload> getTasks() {
    return service.getCreditOfficerTasks();
  }
}