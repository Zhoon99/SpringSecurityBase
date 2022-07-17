package com.example.springsecuritybase.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConfigController {

    @GetMapping("/config")
    public String config(){
        return "admin/config";
    }

    //아니 퓌발 브랜치 왜 안 됨?
}