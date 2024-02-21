package com.xyh.springbootinit.common;

import java.io.Serializable;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 * 删除请求
 *
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    @NotEmpty(message = "用户id不能为空")//@NotBlank 注解主要应用于字符串类型
    private Long[] id;

    private static final long serialVersionUID = 1L;
}