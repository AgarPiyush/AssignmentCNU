package com.cnu2016.controller;

import com.cnu2016.model.Feedback;
import com.cnu2016.repository.FeedbackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Piyush on 7/10/16.
 */

@RestController
public class FeedbackController {
    @Autowired
    FeedbackRepository feedbackCrud;

    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);


    @RequestMapping(value = "/api/contact", method = RequestMethod.POST)
    private ResponseEntity addFeedBack(@RequestBody Feedback p)
    {
        logger.info("Add feeback");
        feedbackCrud.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }
    @RequestMapping(value = "/api/contact", method = RequestMethod.GET)
    private ResponseEntity getAllFeedBack()
    {
        logger.info("get all feedback");
        Iterable<Feedback>p  = feedbackCrud.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(p);
    }
}
