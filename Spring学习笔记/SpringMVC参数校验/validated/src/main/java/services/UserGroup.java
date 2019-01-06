package services;


import inter.GroupA;
import inter.GroupB;

import javax.validation.constraints.*;


public class UserGroup {

    @NotNull(message="id不能为空!",groups = {GroupA.class})
    private Integer id;

    @NotBlank(message="用户名不能为空!",groups = {GroupB.class})
    @Size(min=4,max=12,message="用户名的长度在4~12之间!")
    private String username;

    @NotBlank(message="密码不能为空!")
    private String password;

    @Email(message="非法邮箱!")
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserGroup() {
        super();
    }

}