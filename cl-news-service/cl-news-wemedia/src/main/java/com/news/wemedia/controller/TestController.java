package com.news.wemedia.controller;

import com.news.wemedia.task.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/test")

public class TestController {

    @Autowired
    private Tasks tasks;

    @GetMapping("/async")
    public String testAsync() {

        System.out.println("方法开始执行..." + new Date());
        return "success";
    }
}
