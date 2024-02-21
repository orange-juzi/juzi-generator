package com.xyh.springbootinit.model.dto.user;

import java.io.Serializable;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户更新个人信息请求
 *
 */
@Data
public class UserUpdateMyRequest implements Serializable {

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    private String userName;

    /**
     * 用户头像
     */
    @NotBlank(message = "用户头像不能为空")
    private String userAvatar;

    /**
     * 简介
     */
    @NotBlank(message = "用户简介不能为空")
    private String userProfile;

    private static final long serialVersionUID = 1L;
}