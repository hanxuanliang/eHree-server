package com.hxl.service;

import com.hxl.model.User;
import com.hxl.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * user service
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/5 17:42
 */
@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public User getUserById(Long uid) {
        return userRepository.findFirstById(uid);
    }
}
