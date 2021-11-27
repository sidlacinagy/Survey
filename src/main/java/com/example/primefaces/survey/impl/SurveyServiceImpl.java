package com.example.primefaces.survey.impl;

import com.example.primefaces.survey.SurveyService;
import com.example.primefaces.survey.model.SurveyResultDto;
import com.example.primefaces.survey.persistence.entity.SurveyResult;
import com.example.primefaces.survey.persistence.repository.SurveyResultRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;



@Service
public class SurveyServiceImpl implements SurveyService {


    private SurveyResultRepository surveyResultRepository;

    public SurveyServiceImpl(SurveyResultRepository surveyResultRepository) {
        this.surveyResultRepository = surveyResultRepository;
    }


    @PostMapping
    @Override
    public String saveSurveyResult(String username,String result) {
        SurveyResult surveyResult=ConvertDtoToObject(new SurveyResultDto(username,result));
        if(surveyResultRepository.findById(surveyResult.getUsername()).isPresent()){
            return "You already completed this survey";
        }
        else {
            surveyResultRepository.save(surveyResult);
            return "You successfully completed this survey";
        }
    }

    @PostMapping
    @Override
    public HashMap<String, Integer> getSummedResult() {
        HashMap<String,Integer> map=new HashMap<>();
        List<String> distinctResult = surveyResultRepository.findDistinctResult();
        for(String result : distinctResult){
            Integer numOfResult=surveyResultRepository.countByResult(result);
            map.put(result,numOfResult);
        }
        return map;
    }

    @Override
    public void deleteByUsername(String username) {
        surveyResultRepository.deleteById(username);
    }

    private SurveyResult ConvertDtoToObject(SurveyResultDto surveyResultDto){
        return new SurveyResult(surveyResultDto.getUsername(),surveyResultDto.getResult());
    }

}
