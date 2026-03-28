package com.satwik.question_service.controller;

import com.satwik.question_service.model.Question;
import com.satwik.question_service.model.QuestionWrapper;
import com.satwik.question_service.model.Response;
import com.satwik.question_service.service.QuestionService;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    Environment environment;
    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionById(@PathVariable String category ){
        return questionService.getQuestionByCategory(category);
    }
    @PostMapping("/add ")
    public String addQuestion(@RequestBody Question question){
         return questionService.addQuestion(question);

    }
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName,@RequestParam Integer numQuestions){
        return questionService.getQuestionsForQuiz(categoryName,numQuestions);
    }
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsFromId(questionIds);

    }


    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }
    //generate
    //getQuestions(questionId)
    //getScore

}
