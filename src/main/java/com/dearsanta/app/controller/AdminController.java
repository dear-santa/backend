package com.dearsanta.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class AdminController {

    @RequestMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok().body("Dear Santa api ok");
    }
}