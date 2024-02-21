package com.xyh.springbootinit.model.dto.user;

import com.xyh.springbootinit.common.PageRequest;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 用户查询请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
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
    private String userAvatar;

    /**
     * 用户角色：user/admin
     */
    @NotBlank(message = "用户角色不能为空")
    private String userRole;

    private static final long serialVersionUID = 1L;
}