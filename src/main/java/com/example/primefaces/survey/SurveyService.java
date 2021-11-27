package com.example.primefaces.survey;

import com.example.primefaces.survey.model.SurveyResultDto;

import java.util.HashMap;

public interface SurveyService {

    String saveSurveyResult(String username,String result);

    HashMap<String,Integer> getSummedResult();

    void deleteByUsername(String username);

}
