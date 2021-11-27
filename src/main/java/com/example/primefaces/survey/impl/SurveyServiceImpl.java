package com.example.primefaces.survey.impl;

import com.example.primefaces.survey.formhandling.Vote;
import com.example.primefaces.survey.model.SurveyResultDto;
import com.example.primefaces.survey.persistence.entity.SurveyResult;
import com.example.primefaces.survey.persistence.repository.SurveyResultRepository;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;



@RestController
@RequestMapping
public class SurveyServiceImpl {


    private SurveyResultRepository surveyResultRepository;


    public SurveyServiceImpl(SurveyResultRepository surveyResultRepository) {
        this.surveyResultRepository = surveyResultRepository;
    }


    @PostMapping(path="/home")
    public String saveSurveyResult(@ModelAttribute Vote vote) {
        SurveyResult surveyResult=ConvertDtoToObject(new SurveyResultDto(vote.getUsername(), vote.getResult()));
        System.out.println(surveyResult);
        if(surveyResultRepository.findById(surveyResult.getUsername()).isPresent()){
            return "You already completed this survey";
        }
        else {
            surveyResultRepository.save(surveyResult);
            return "You successfully completed this survey";
        }
    }

    @PostMapping
    public HashMap<String, Integer> getSummedResult() {
        HashMap<String,Integer> map=new HashMap<>();
        List<String> distinctResult = surveyResultRepository.findDistinctResult();
        for(String result : distinctResult){
            Integer numOfResult=surveyResultRepository.countByResult(result);
            map.put(result,numOfResult);
        }
        return map;
    }

    public void deleteByUsername(String username) {
        surveyResultRepository.deleteById(username);
    }

    private SurveyResult ConvertDtoToObject(SurveyResultDto surveyResultDto){
        return new SurveyResult(surveyResultDto.getUsername(),surveyResultDto.getResult());
    }

}
