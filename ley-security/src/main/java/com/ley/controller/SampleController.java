package com.ley.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author Leigh Yu
 * @date 2020/8/18 22:14
 */
@RestController
public class SampleController {
    @GetMapping("/person/{id}")
    public String get(@RequestParam("id") String id) {
        return id;
    }

    @GetMapping("/person/all")
    public String getAll() {
        return "this is all person";
    }

    @PostMapping("/login")
    public String login() {
        return "login page";
    }
}
