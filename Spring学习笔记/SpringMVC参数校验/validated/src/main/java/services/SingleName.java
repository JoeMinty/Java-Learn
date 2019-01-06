package services;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Service
public class SingleName {
    @NotBlank(message = "用户名不能为空!")
    @Size(min = 4, max = 12, message = "用户名的长度在4~12之间!")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
