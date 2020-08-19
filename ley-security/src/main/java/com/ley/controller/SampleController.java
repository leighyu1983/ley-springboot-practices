package com.ley.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leigh Yu
 * @date 2020/8/18 22:14
 */
@RestController
public class SampleController {
    @Autowired private HttpServletRequest request;

    @GetMapping("/person/{id}")
    public String get(@RequestParam("id") String id) {
        return id;
    }

    @GetMapping("/person/all")
    public String getAll() {
        return "this is all person";
    }

    @PostMapping("/my-index")
    public String index() {
        return "my index no need permission";
    }

    @PostMapping("/my-login")
    public String login() {
        return "login page";
    }
}
