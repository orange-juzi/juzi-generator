package com.xyh.springbootinit.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyh.springbootinit.common.Result;
import com.xyh.springbootinit.common.DeleteRequest;
import com.xyh.springbootinit.common.ResultCodeEnum;
import com.xyh.springbootinit.common.ResultUtils;
import com.xyh.springbootinit.constant.UserConstant;
import com.xyh.springbootinit.exception.BusinessException;
import com.xyh.springbootinit.exception.ThrowUtils;
import com.xyh.springbootinit.model.dto.user.UserAddRequest;
import com.xyh.springbootinit.model.dto.user.UserLoginRequest;
import com.xyh.springbootinit.model.dto.user.UserQueryRequest;
import com.xyh.springbootinit.model.dto.user.UserRegisterRequest;
import com.xyh.springbootinit.model.dto.user.UserUpdateMyRequest;
import com.xyh.springbootinit.model.dto.user.UserUpdateRequest;
import com.xyh.springbootinit.model.entity.User;
import com.xyh.springbootinit.model.vo.LoginUserVO;
import com.xyh.springbootinit.model.vo.UserVO;
import com.xyh.springbootinit.service.UserService;

import java.util.Arrays;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public Result<Long> userRegister(@Validated @RequestBody UserRegisterRequest userRegisterRequest) {
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @return
     */
    @PostMapping("/login")
    public Result<HashMap<String, Object>> userLogin(@Validated @RequestBody UserLoginRequest userLoginRequest) {
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword);
        // 会话登录
        StpUtil.login(loginUserVO.getId());
        // 获取token
        String token = StpUtil.getTokenValue();
        HashMap<String, Object> map = new HashMap<>();
        map.put("user", loginUserVO);
        map.put(StpUtil.getTokenName(), token);
        return ResultUtils.success(map);
    }

    /**
     * 用户注销
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<Boolean> userLogout() {
        StpUtil.logout();
        return ResultUtils.success(null);
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    @GetMapping("/get/login")
    @SaCheckLogin
    public Result<LoginUserVO> getCurrentUser() {
        User user = userService.getCurrentUser();
        LoginUserVO loginUserVO = userService.getLoginUserVO(user);
        return ResultUtils.success(loginUserVO);
    }

    /**
     * 创建用户
     *
     * @param userAddRequest
     * @return
     */
    @PostMapping("/add")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public Result<Long> addUser(@Validated @RequestBody UserAddRequest userAddRequest) {
        Long userId = userService.addUser(userAddRequest);
        return ResultUtils.success(userId);
    }

    /**
     * 删除用户
     *
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public Result<Boolean> deleteUser(@Validated @RequestBody DeleteRequest deleteRequest) {
        boolean b = userService.removeByIds(Arrays.asList(deleteRequest.getId()));
        if (b) {
            for (Long id : deleteRequest.getId()) {
                StpUtil.logout(id);
            }
        }
        return ResultUtils.success(b);
    }

    /**
     * 更新用户
     *
     * @param userUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public Result<Boolean> updateUser(@Validated @RequestBody UserUpdateRequest userUpdateRequest) {
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ResultCodeEnum.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取用户（仅管理员）
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    @SaCheckRole(value = UserConstant.ADMIN_ROLE)
    public Result<User> getUserById(long id) {
        if (id <= 0) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, ResultCodeEnum.NOT_FOUND_ERROR);
        return ResultUtils.success(user);
    }

    /**
     * 根据 id 获取包装类
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/get/vo")
    public Result<UserVO> getUserVOById(long id, HttpServletRequest request) {
        Result<User> response = getUserById(id);
        User user = response.getData();
        return ResultUtils.success(userService.getUserVO(user));
    }


    /**
     * 分页获取用户列表（仅管理员）
     *
     * @param userQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public Result<Page<User>> listUserByPage(@RequestBody UserQueryRequest userQueryRequest) {
        Page<User> userPage = userService.listUserByPage(userQueryRequest);
        return ResultUtils.success(userPage);
    }

    /**
     * 分页获取用户封装列表
     *
     * @param userQueryRequest
     * @return
     */
    @PostMapping("/list/page/vo")
    public Result<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
        return ResultUtils.success(userVOPage);
    }


    /**
     * 更新个人信息
     *
     * @param userUpdateMyRequest
     * @return
     */
    @PostMapping("/update/my")
    public Result<Boolean> updateMyUser(@Validated @RequestBody UserUpdateMyRequest userUpdateMyRequest) {
        User loginUser = userService.getCurrentUser();
        User user = new User();
        BeanUtils.copyProperties(userUpdateMyRequest, user);
        user.setId(loginUser.getId());
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ResultCodeEnum.OPERATION_ERROR);
        return ResultUtils.success(true);
    }
}
