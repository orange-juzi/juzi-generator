package com.xyh.springbootinit.model.dto.user;

import java.io.Serializable;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

/**
 * 用户登录请求
 *
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 4, message = "账号长度不得小于4位")
    private String userAccount;

    @NotBlank(message = "密码不能为空")
    @Length(min = 8, message = "密码长度不得小于8位")
    private String userPassword;
}
