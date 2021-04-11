package com.instamoolah.loans.services;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.instamoolah.loans.core.LoanApplication;

@Service
public class AffordabilityService {

    @Autowired
    private KieContainer kContainer;

    public LoanApplication checkAffordability(LoanApplication application) {
        KieSession kieSession = kContainer.newKieSession();
        kieSession.setGlobal("loanApplication", application);
        kieSession.insert(application);
        kieSession.fireAllRules();
        kieSession.dispose();
        return application;
    }
}
