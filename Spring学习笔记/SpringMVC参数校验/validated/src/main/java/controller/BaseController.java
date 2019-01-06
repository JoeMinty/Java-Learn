package controller;

import inter.GroupB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import services.SingleName;
import services.User;
import services.UserGroup;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@Validated
public class BaseController {

    @Autowired
    SingleName name;

    @RequestMapping(value = "/getvalidated",method = RequestMethod.GET)
    public SingleName validated(@Valid SingleName name) {
        return name;
    }

    @RequestMapping(value = "/getvalidateds",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String validated(@NotBlank(message = "名字不能为kong") String name) {
        return name;
    }

    @RequestMapping("/validated")
    public User validated(@Valid User user) {
//        , BindingResult result
//        if (result.hasErrors()) {
//            List<ObjectError> errors = result.getAllErrors();
//            for (ObjectError error : errors) {
//                System.out.println(error.getDefaultMessage());
//            }
//        }
        return user;
    }

    @RequestMapping("/validatedgroup")
    public UserGroup validatedgroup(@Validated(value= {GroupB.class}) UserGroup usergroup, BindingResult result) {

        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                System.out.println(error.getDefaultMessage());
            }
        }
        return usergroup;
    }

    @RequestMapping("/test")
    public String test() {

        return "Hello Tomcat";
    }

}