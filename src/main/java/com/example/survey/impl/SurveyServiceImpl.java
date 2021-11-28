package com.example.survey.impl;

import com.example.survey.formhandling.Vote;
import com.example.survey.model.SurveyResultDto;
import com.example.survey.persistence.entity.SurveyResult;
import com.example.survey.persistence.repository.SurveyResultRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;



@RestController
@RequestMapping
@CrossOrigin
public class SurveyServiceImpl {


    private SurveyResultRepository surveyResultRepository;



    public SurveyServiceImpl(SurveyResultRepository surveyResultRepository) {
        this.surveyResultRepository = surveyResultRepository;
    }


    @PostMapping(path="/api/submit",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> saveSurveyResult(@ModelAttribute Vote vote) {
        SurveyResult surveyResult=ConvertDtoToObject(new SurveyResultDto(vote.getUsername(), vote.getResult()));
        if(surveyResultRepository.findById(surveyResult.getUsername()).isPresent()){
            return new ResponseEntity<>("\"You already voted\"", HttpStatus.OK);
        }
        else {
            surveyResultRepository.save(surveyResult);
            return new ResponseEntity<>("\"Success\"", HttpStatus.OK);
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
