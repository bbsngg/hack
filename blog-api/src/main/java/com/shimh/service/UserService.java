package com.shimh.service;

import java.util.List;

import com.shimh.entity.User;

/**
 * @author CSE
 * <p>
 * 2019年1月23日
 */
public interface UserService {

    List<User> findAll();

    User getUserByAccount(String account);

    User getUserById(Long id);

    Long saveUser(User user);

    Long updateUser(User user);

    void deleteUserById(Long id);
}
