package com.moons.paramcheck.controller;

import com.google.gson.Gson;
import com.moons.paramcheck.annotation.NotNull;
import com.moons.paramcheck.annotation.ValidParam;
import com.moons.paramcheck.requestParam.StudentParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    private static Gson gson = new Gson();

    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public StudentParam checkParam(@ValidParam StudentParam param, @NotNull Integer limit) {
        System.out.println(System.getProperty("env"));
        System.out.println(gson.toJson(param));
        System.out.println(limit);
        return param;
    }

    @ResponseBody
    @RequestMapping(value="/moons")
    public String sayChinese(String name){
        System.out.println("我进来了");
        return name;
    }

}
