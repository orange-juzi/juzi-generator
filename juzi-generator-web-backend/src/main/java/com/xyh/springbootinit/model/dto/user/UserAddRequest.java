package com.xyh.springbootinit.model.dto.user;

import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户创建请求
 *
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    private String userName;

    /**
     * 账号
     */
    @NotBlank(message = "用户账号不能为空")
    private String userAccount;

    /**
     * 用户头像
     */
    @NotBlank(message = "用户头像不能为空")
    private String userAvatar;

    /**
     * 用户角色: user, admin
     */
    @NotBlank(message = "用户角色不能为空")
    @Pattern(regexp = "^user$|^admin$", message = "用户角色内容不正确")
    private String userRole;

    private static final long serialVersionUID = 1L;
}