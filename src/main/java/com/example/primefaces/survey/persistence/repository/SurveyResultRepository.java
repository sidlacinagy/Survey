package com.example.primefaces.survey.persistence.repository;

import com.example.primefaces.survey.persistence.entity.SurveyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurveyResultRepository extends JpaRepository<SurveyResult,String> {

    @Query("select DISTINCT(r.result) from SurveyResult r")
    List<String> findDistinctResult();

    int countByResult(String result);


}
