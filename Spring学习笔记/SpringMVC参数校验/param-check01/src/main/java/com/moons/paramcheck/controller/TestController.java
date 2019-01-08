package com.moons.paramcheck.controller;

import com.alibaba.fastjson.JSONObject;
import com.moons.paramcheck.annotation.ParamValid;
import com.moons.paramcheck.annotation.ParameterValid;
import com.moons.paramcheck.annotation.PathAndQueryParamValid;
import com.moons.paramcheck.requestParam.ResponseVO;
import com.moons.paramcheck.requestParam.ResponseVOAL;
import com.moons.paramcheck.requestParam.TestVO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @ResponseBody
    @PostMapping("body")
    public ResponseVO bodyPost(@RequestBody @Valid TestVO body, BindingResult result){
        System.out.println("come in");
        //校验到错误
        System.out.println(result);
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            List<String> lists = new ArrayList<>();
            for (ObjectError objectError : allErrors) {
                lists.add(objectError.getDefaultMessage());
            }
            return new ResponseVO(HttpStatus.BAD_REQUEST.value(), "parameter empty", lists);
        }
        return new ResponseVO(HttpStatus.OK.value(), "bodyPost", null);
    }

    @ParamValid
    @ResponseBody
    @PostMapping("bodyan")
    public ResponseVO bodyPostAn(@RequestBody @Valid TestVO body,BindingResult result){
        return new ResponseVO(200,"bodyPost", null);
    }

    @ResponseBody
    @GetMapping("path/{isInt}/{isString}")
    public ResponseVOAL pathGet(@PathVariable Integer isInt,
                                @PathVariable String isString) {

        System.out.println("int:" + isInt);
        System.out.println("String:" + isString);
        JSONObject resultJson = new JSONObject();
        resultJson.put("isInt", isInt);
        resultJson.put("isString", isString);
        return new ResponseVOAL(HttpStatus.OK.value(), "pathGet", resultJson);
    }

    @ResponseBody
    @GetMapping("query")
    public ResponseVOAL queryGet(@RequestParam Integer isInt,
                               @RequestParam String isString) {
        System.out.println("int:" + isInt);
        System.out.println("String:" + isString);

        JSONObject resultJson = new JSONObject();
        resultJson.put("isInt", isInt);
        resultJson.put("isString", isString);
        return new ResponseVOAL(HttpStatus.OK.value(), "queryGet", resultJson);
    }


    @PathAndQueryParamValid
    @GetMapping("pathao/{isInt}/{isString}")
    public ResponseVOAL pathGetAo(@PathVariable @ParameterValid(type = Integer.class, msg = "isInt must be more than 2", isMin = true, min = 2) Integer isInt,
                              @PathVariable @ParameterValid(type = String.class, msg = "isString is empty") String isString) {
        System.out.println("int:" + isInt);
        System.out.println("String:" + isString);
        JSONObject resultJson = new JSONObject();
        resultJson.put("isInt", isInt);
        resultJson.put("isString", isString);
        return new ResponseVOAL(HttpStatus.OK.value(), "pathGet", resultJson);
    }
    @GetMapping("queryao")
    @PathAndQueryParamValid
    public ResponseVOAL queryGetAo(@RequestParam @ParameterValid(type = Integer.class, msg = "isInt must be more than 2 ", isMin = true, min = 2) Integer isInt,
                               @RequestParam @ParameterValid(type = String.class, msg = "isString is empty") String isString) {
        System.out.println("int:" + isInt);
        System.out.println("String:" + isString);
        JSONObject resultJson = new JSONObject();
        resultJson.put("isInt", isInt);
        resultJson.put("isString", isString);
        return new ResponseVOAL(HttpStatus.OK.value(), "queryGet", resultJson);
    }






}
