package com.hxl.core;

import com.hxl.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板中也存在这种设计，使用 ThreadLocal 来解决并发中的当前用户的问题
 * 【千万要记住 手动remove，不然会有用户信息重复的问题】
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/5 17:45
 */
public class LocalUser {

    private static ThreadLocal<Map<String, Object>> localUser = new ThreadLocal<>();

    public static User getLocalUser() {
        Map<String, Object> map = LocalUser.localUser.get();
        return (User) map.get("localuser");
    }

    public static Integer getScope() {
        Map<String, Object> map = LocalUser.localUser.get();
        return (Integer) map.get("scope");
    }

    public static void setLocalUser(User user, Integer scope) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("localuser", user);
        map.put("scope", scope);
        LocalUser.localUser.set(map);
    }

    public static void removeLocalUser() {
        LocalUser.localUser.remove();
    }
}
