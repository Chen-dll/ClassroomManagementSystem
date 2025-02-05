package com.ChenDll.classroommanagement.user;

import com.ChenDll.classroommanagement.constants.Role;

public class UserService {
    private static final UserDao userDao = new UserDao();

    // UserService.java
    public static boolean registerUser(String username, String password, Role role, String studentId, String department, String idCard) {
        // 检查用户名是否已存在
        if (UserDao.checkUsernameExists(username)) {
            return false; // 如果用户名存在，返回 false
        }
        // 如果用户名不存在，继续执行注册
        return UserDao.addUser(username, password, role, studentId, department, idCard) != -1;
    }

    // 用户登录
    public static boolean login(String username, String password) {
        return userDao.loginUser(username, password);
    }

    // 获取用户信息
//    public User getUserInfo(String username) {
//        return userDao.getUserByUsername(username);
//    }

    // 获取用户角色
    public User getUserRole(String username) {
        return userDao.getUserByUsername(username);
    }

    // 获取所有用户
//    public List<User> getAllUsers() {
//        return userDao.getAllUsers();
//    }
//
//    // 删除用户
//    public boolean removeUser(int id) {
//        return userDao.deleteUser(id);
//    }
}

