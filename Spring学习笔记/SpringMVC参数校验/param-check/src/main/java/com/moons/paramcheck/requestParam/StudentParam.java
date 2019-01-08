package com.moons.paramcheck.requestParam;

import com.moons.paramcheck.annotation.NotEmpty;
import com.moons.paramcheck.annotation.NotNull;

public class StudentParam {

    @NotNull
    private Integer id;
    private Integer age;
    @NotEmpty
    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
