package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WelcomeController {


    @GetMapping
    public ResponseEntity<ResponseWrapper> getTasks() {
        return ResponseEntity.ok(new ResponseWrapper("List all tasks", "welcome", HttpStatus.OK));
    }

}
