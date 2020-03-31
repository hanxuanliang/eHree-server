package com.hxl.repository;

import com.hxl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 用户 dao层
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/30 13:46
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Optional<User> findByOpenId(String openId);

    User findFirstById(Long id);

    User findByUnifyUid(Long id);
}
