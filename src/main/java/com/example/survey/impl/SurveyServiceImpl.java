package com.example.survey.impl;

import com.example.survey.formhandling.Vote;
import com.example.survey.model.SurveyResultDto;
import com.example.survey.persistence.entity.SurveyResult;
import com.example.survey.persistence.repository.SurveyResultRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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


    @PostMapping(path = "/api/submit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> saveSurveyResult(@ModelAttribute Vote vote) {
        SurveyResult surveyResult = ConvertDtoToObject(new SurveyResultDto(vote.getUsername(), vote.getResult()));
        System.out.println(vote.getResult());
        if (surveyResultRepository.findById(surveyResult.getUsername()).isPresent()) {
            return new ResponseEntity<>("\"You already voted\"", HttpStatus.OK);
        } else {
            surveyResultRepository.save(surveyResult);
            return new ResponseEntity<>("\"Success\"", HttpStatus.OK);
        }
    }

    @GetMapping(path = "/api/result", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  getSummedResult() {
        HashMap<String, Integer> map = new HashMap<>();
        List<String> distinctResult = surveyResultRepository.findDistinctResult();
        distinctResult.remove(null);
        for (String result : distinctResult) {
            Integer numOfResult = surveyResultRepository.countByResult(result);
            map.put(result, numOfResult);
        }
        System.out.println(distinctResult);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(map);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    public void deleteByUsername(String username) {
        surveyResultRepository.deleteById(username);
    }

    private SurveyResult ConvertDtoToObject(SurveyResultDto surveyResultDto) {
        return new SurveyResult(surveyResultDto.getUsername(), surveyResultDto.getResult());
    }

}
