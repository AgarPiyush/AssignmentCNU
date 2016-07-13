package com.cnu2016.controller;

import com.cnu2016.model.Feedback;
import com.cnu2016.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Piyush on 7/10/16.
 */

@RestController
public class FeedbackController {
    @Autowired
    FeedbackRepository feedbackCrud;


    private ResponseEntity ifNullNotFound() {
        Map<String, String> hmap = new HashMap<String, String>();
        hmap.put("detail", "Not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hmap);
    }

        @RequestMapping(value = "/api/contact", method = RequestMethod.POST)
    private ResponseEntity addFeedBack(@RequestBody Feedback p)
    {
        feedbackCrud.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }
    @RequestMapping(value = "/api/contact", method = RequestMethod.GET)
    private ResponseEntity getAllFeedBack()
    {
        Iterable<Feedback>p  = feedbackCrud.findAll();
        if(p == null)
            return  ifNullNotFound();
        return ResponseEntity.status(HttpStatus.OK).body(p);
    }
}
