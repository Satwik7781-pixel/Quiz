package com.satwik.quizapp.service;

import com.satwik.quizapp.model.Question;
import com.satwik.quizapp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
