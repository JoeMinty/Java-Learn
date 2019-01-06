package controller;

import inter.GroupB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import services.SingleName;
import services.User;
import services.UserGroup;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@Validated
public class BaseController {

    @Autowired
    SingleName name;

    @RequestMapping(value = "/getvalidated/{name}/{age}",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String validated(@NotBlank(message = "名字不能为空") @PathVariable String name, @Min(2) @PathVariable Integer age) {
        //new ResponseVO();
        return "name: " +name +"," + "age: "+age;
    }

    @RequestMapping(value = "/getvalidated",method = RequestMethod.GET)
    public SingleName validated(@Valid SingleName name) {
        return name;
    }

    @RequestMapping(value = "/getvalidateds",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String validated(@NotBlank(message = "名字不能为kong") String name) {
        return name;
    }

    @RequestMapping(value = "/validated",produces="application/json;;charset=UTF-8")
    public User validated(@Validated User user , BindingResult result) {

        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                System.out.println(error.getDefaultMessage());
            }
        }
        return user;
    }

    @RequestMapping(value = "/validatedgroup")
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