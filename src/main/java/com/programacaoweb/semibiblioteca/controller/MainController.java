package com.programacaoweb.semibiblioteca.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public ResponseEntity<Object> index() {
        return ResponseEntity.ok("SEMI BIBLIOTECA");
    }

}
