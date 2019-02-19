package com.collmall.controller;


import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BeetlAction {

    @GetMapping({"/", "/index", "/beetl"})
    public String beetl(Model model) {
        model.addAttribute("beetl", "测试一下通过模板引擎传递参数！");
        return "index.html";
    }

}