package com.instamoolah.loans.services;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instamoolah.loans.core.Applicant;

@Service
public class AffordabilityService {

    @Autowired
    private KieContainer kContainer;

    public Applicant checkAffordability(Applicant applicant) {
        KieSession kieSession = kContainer.newKieSession();
        kieSession.setGlobal("applicant", applicant);
        kieSession.insert(applicant);
        kieSession.fireAllRules();
        kieSession.dispose();
        return applicant;
    }
}
