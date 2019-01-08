package com.moons.paramcheck.requestParam;


import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class TestVO {
    @Getter
    @Setter
    @Min(value = 0,message = "请求参数isString不能小于0")
    private Integer isInt;
    @Getter
    @Setter
    @NotBlank(message = "请求参数isString不能为空")
    private String isString;
}


