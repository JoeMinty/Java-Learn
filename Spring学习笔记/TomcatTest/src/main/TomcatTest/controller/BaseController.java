package controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.User;

import java.util.List;

@RestController
public class BaseController {


    @RequestMapping("/validated")
    public User validated(@Validated User user, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                System.out.println(error.getDefaultMessage());
            }
        }
        return user;
    }

    @RequestMapping("/test")
    public String test() {
        System.out.println("Hello Tomcat");
        return "Hello Tomcat";
    }

    @RequestMapping("/test1")
    public String test1() {
        System.out.println("Hello Test1");
        return "Hello Test1";
    }

}