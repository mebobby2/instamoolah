package com.instamoolah.loans.services;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.instamoolah.loans.core.LoanApplication;

@Service
public class AutoApproveService {

    @Autowired
    private KieContainer kContainer;

    public LoanApplication checkAutoApprove(LoanApplication application) {
        KieSession kieSession = kContainer.newKieSession();
        kieSession.setGlobal("loanApplication", application);
        kieSession.insert(application);
        kieSession.fireAllRules();
        kieSession.dispose();
        return application;
    }
}
