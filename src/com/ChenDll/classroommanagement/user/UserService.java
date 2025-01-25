package com.ChenDll.classroommanagement.user;

public class UserService {
    private final UserDao userDao = new UserDao();

    // 注册用户
    public boolean register(String username, String password, String role) {
        // 自动生成用户ID（模拟数据库自增ID）
        int newId = userDao.getAllUsers().size() + 1;
        User newUser = new User(newId, username, password, role);
        return userDao.addUser(newUser);
    }

    // 用户登录
    public User login(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password);
    }

    // 获取所有用户（调试用）
    public void printAllUsers() {
        for (User user : userDao.getAllUsers()) {
            System.out.println(user);
        }
    }
}