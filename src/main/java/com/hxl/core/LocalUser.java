package com.hxl.core;

import com.hxl.model.User;

/**
 * 模板中也存在这种设计，使用 ThreadLocal 来解决并发中的当前用户的问题
 * 【千万要记住 手动remove，不然会有用户信息重复的问题】
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/5 17:45
 */
public class LocalUser {

    private static ThreadLocal<User> localUser = new ThreadLocal<>();

    public static User getLocalUser() {
        return LocalUser.localUser.get();
    }

    public static void setLocalUser(User user) {
        LocalUser.localUser.set(user);
    }

    public static <T> T getLocalUser(Class<T> clazz) {
        return (T) localUser.get();
    }

    public static void removeLocalUser() {
        LocalUser.localUser.remove();
    }
}
