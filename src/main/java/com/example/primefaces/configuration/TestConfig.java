package com.example.primefaces.configuration;

import com.example.primefaces.survey.SurveyService;
import com.example.primefaces.survey.model.SurveyResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestConfig {

    @Autowired
    private SurveyService surveyService;



    @PostConstruct
    public void init() {

        System.out.println(surveyService.saveSurveyResult("fa","f"));
        System.out.println(surveyService.saveSurveyResult("fa","g"));
        System.out.println(surveyService.saveSurveyResult("as","f"));
        System.out.println(surveyService.saveSurveyResult("ke","b"));
        System.out.println(surveyService.getSummedResult());


    }
}
