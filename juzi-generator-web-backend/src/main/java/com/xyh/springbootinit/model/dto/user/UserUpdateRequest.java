package com.xyh.springbootinit.model.dto.user;

import java.io.Serializable;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户更新请求
 *
 */
@Data
public class UserUpdateRequest implements Serializable {
    /**
     * id
     */
    @NotBlank(message = "用户id不能为空")
    private Long id;

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
     * 用户角色：user/admin/ban
     */
    @NotBlank(message = "用户角色不能为空")
    @Pattern(regexp = "^user$|^admin$", message = "用户角色内容不正确")
    private String userRole;

    private static final long serialVersionUID = 1L;
}