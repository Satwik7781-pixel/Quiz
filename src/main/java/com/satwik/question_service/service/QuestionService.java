package com.satwik.question_service.service;


import com.satwik.question_service.dao.QuestionDao;
import com.satwik.question_service.model.Question;
import com.satwik.question_service.model.QuestionWrapper;
import com.satwik.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
@Autowired
QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {

        List<Question> questions = questionDao.findByCategoryIgnoreCase(category);

        if (questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }

        return new ResponseEntity<>(questions, HttpStatus.OK); // 200
    }

    public String addQuestion(Question question) {
        questionDao.save(question);
        return "question saved successfully";
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions= questionDao.findRandomQuestionsByCategory(categoryName,numQuestions);
return new ResponseEntity<>(questions,HttpStatus.OK);

    }


    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {

        List<QuestionWrapper> wrappers = new ArrayList<>();

        for(Integer id : questionIds){

            Question question = questionDao.findById(id)
                    .orElseThrow(() -> new RuntimeException("Question not found: " + id));

            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());

            wrappers.add(wrapper);
        }

        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {


        int right=0;

        for(Response response:responses){
            Question question = questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer()))
                right++;


        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
