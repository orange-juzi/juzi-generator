package com.xyh.springbootinit.config;

import cn.dev33.satoken.stp.StpInterface;
import com.xyh.springbootinit.model.entity.User;
import com.xyh.springbootinit.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private UserService userService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return new ArrayList<String>();
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Long userId = Long.valueOf((String) loginId);
        List<String> list = new ArrayList<String>();
        User user = userService.getById(userId);
        list.add(user.getUserRole());
        return list;
    }

}
