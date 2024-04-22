package com.duaphine.blogger.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
public class helloWordController {
    @GetMapping("hello-world")
    public  String helloWorld(){
        return "Hello World!";
    }

    @GetMapping("hello")
    public String helloByName(@RequestParam String name){
        return "Hello "+name;
    }

    @GetMapping("hello/{name}")
    public String hello(@PathVariable String name){
        return "Hello "+name;
    }


}
