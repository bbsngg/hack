package com.shimh.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.shimh.common.annotation.LogAnnotation;
import com.shimh.entity.Article;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shimh.common.constant.Base;
import com.shimh.common.constant.ResultCode;
import com.shimh.common.result.Result;
import com.shimh.common.util.UserUtils;
import com.shimh.entity.User;
import com.shimh.service.UserService;

/**
 * 用户api
 *
 * @author CSE
 * <p>
 * 2019年1月23日
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @LogAnnotation(module = "用户", operation = "获取所有用户")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result listUsers() {
        List<User> users = userService.findAll();

        return Result.success(users);
    }

    @GetMapping("/{id}")
    @LogAnnotation(module = "用户", operation = "根据id获取用户")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result getUserById(@PathVariable("id") Long id) {

        Result r = new Result();

        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }

        User user = userService.getUserById(id);

        r.setResultCode(ResultCode.SUCCESS);
        r.setData(user);
        return r;
    }

    @GetMapping("/currentUser")
    @FastJsonView(
            include = {@FastJsonFilter(clazz = User.class, props = {"id", "account", "nickname", "avatar"})})
    @LogAnnotation(module = "用户", operation = "获取当前登录用户")
    public Result getCurrentUser(HttpServletRequest request) {

        Result r = new Result();

        User currentUser = UserUtils.getCurrentUser();

        r.setResultCode(ResultCode.SUCCESS);
        r.setData(currentUser);
        return r;
    }

    @PostMapping("/create")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "用户", operation = "添加用户")
    public Result saveUser(@Validated @RequestBody User user) {

        Long userId = userService.saveUser(user);

        Result r = Result.success();
        r.simple().put("userId", userId);
        return r;
    }

    @PostMapping("/update")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "用户", operation = "修改用户")
    public Result updateUser(@RequestBody User user) {
        Result r = new Result();

//        if (null == user.getId()) {
////            r.setResultCode(ResultCode.USER_NOT_EXIST);
////            return r;
////        }

        Long userId = userService.updateUser(user);

        r.setResultCode(ResultCode.SUCCESS);
        r.simple().put("userId", userId);
        return r;
    }

    @GetMapping("/delete/{id}")
    @RequiresRoles(Base.ROLE_ADMIN)
    @LogAnnotation(module = "用户", operation = "删除用户")
    public Result deleteUserById(@PathVariable("id") Long id) {
        Result r = new Result();

        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }

        userService.deleteUserById(id);

        r.setResultCode(ResultCode.SUCCESS);
        return r;
    }

    //新增个人中心部分操作

    //更改邮箱
    @PostMapping("/setEmail")
    @RequiresAuthentication
    @LogAnnotation(module = "信息更改", operation = "设置邮箱")
    public Result setEmail(@Validated @RequestBody User user) {
        System.out.println(user.getEmail());
        System.out.print("--------------------------------------------------------------------");
        System.out.print(user);
//        return  null;
        Result r = new Result();
        Long userId = userService.updateUser(user);

        r.setResultCode(ResultCode.SUCCESS);
        r.simple().put("userId", userId);
        return r;

//        return updateUser(user);
    }

    @PostMapping("/setSchool")
    @RequiresAuthentication
    @LogAnnotation(module = "信息更改", operation = "设置学校")
    public Result setSchool(@Validated @RequestBody User user) {

        return updateUser(user);
    }

    @PostMapping("/setGrade")
    @RequiresAuthentication
    @LogAnnotation(module = "信息更改", operation = "设置年级")
    public Result setGrade(@Validated @RequestBody User user) {

        return updateUser(user);
    }

    @PostMapping("/setQQ")
    @RequiresAuthentication
    @LogAnnotation(module = "信息更改", operation = "设置QQ")
    public Result setQQ(@Validated @RequestBody User user) {

        return updateUser(user);
    }

    @PostMapping("/setWechat")
    @RequiresAuthentication
    @LogAnnotation(module = "信息更改", operation = "设置微信")
    public Result setWechat(@Validated @RequestBody User user) {
        return updateUser(user);
    }
}
