package com.xyh.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyh.springbootinit.model.dto.user.UserAddRequest;
import com.xyh.springbootinit.model.dto.user.UserQueryRequest;
import com.xyh.springbootinit.model.entity.User;
import com.xyh.springbootinit.model.vo.LoginUserVO;
import com.xyh.springbootinit.model.vo.UserVO;
import java.util.List;

/**
 * 用户服务
 *
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword);

    /**
     * 获取当前登录用户
     *
     * @return
     */
    User getCurrentUser();


    /**
     * 获取脱敏的已登录用户信息
     *
     * @return
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param userList
     * @return
     */
    List<UserVO> getUserVO(List<User> userList);

    /**
     * 获取查询条件
     *
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 分页获取用户列表（仅管理员）
     * @param userQueryRequest
     * @return
     */
    Page<User> listUserByPage(UserQueryRequest userQueryRequest);

    /**
     * 分页获取脱敏用户列表
     *
     * @param userQueryRequest
     * @return
     */
    Page<UserVO> listUserVOByPage(UserQueryRequest userQueryRequest);

    /**
     * 添加用户
     * @param userAddRequest
     * @return
     */
    Long addUser(UserAddRequest userAddRequest);
}
