package com.satwik.quizapp.controller;

import com.satwik.quizapp.model.Question;
import com.satwik.quizapp.model.QuestionWrapper;
import com.satwik.quizapp.model.Response;
import com.satwik.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
    @RequestMapping("quiz")
    public class Quizcontroller{
        @Autowired
        QuizService quizService;

        @PostMapping("create")
        public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam int numQ,@RequestParam String title) {
            return quizService.createQuiz(category,numQ,title);

        }
        @GetMapping("get/{id}")
        public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
     return quizService.getQuizQuestions(id);
        }
        @PostMapping("submit/{id}")
    public ResponseEntity<String> submitQuiz(@PathVariable Integer id,@RequestBody List<Response> response ){
          return quizService.calculateResult(id,response);


    }
}


