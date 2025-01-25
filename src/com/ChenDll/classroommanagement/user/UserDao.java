package com.ChenDll.classroommanagement.user;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final List<User> users = new ArrayList<>();  // 模拟数据库存储

    // 添加用户
    public boolean addUser(User user) {
        // 检查用户名是否已存在
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                return false;
            }
        }
        users.add(user);
        return true;
    }

    // 根据用户名和密码查找用户（用于登录）
    public User findByUsernameAndPassword(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // 获取所有用户（调试用）
    public List<User> getAllUsers() {
        return users;
    }
}