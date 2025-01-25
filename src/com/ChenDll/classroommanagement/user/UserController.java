package com.ChenDll.classroommanagement.user;

import com.ChenDll.classroommanagement.util.InputUtil;

import java.util.concurrent.TimeUnit;

public class UserController {
    private final UserService userService = new UserService();
    private User currentUser; // 当前登录的用户

    // 注册用户
    public void register() {
        System.out.println("\n=== 用户注册 ===");
        String username = InputUtil.getString("请输入用户名：");
        String password = InputUtil.getString("请输入密码：");
        String role = InputUtil.getString("请输入角色（STUDENT/ADMIN）：").toUpperCase();

        boolean success = userService.register(username, password, role);
        System.out.println(success ? "注册成功！" : "注册失败，用户名已存在！");
    }

    // 登录用户（新增三次重试逻辑）
    public void login() {
        System.out.println("\n=== 用户登录 ===");

        for (int attempts = 0; attempts < 3; attempts++) {
            String username = InputUtil.getString("请输入用户名：");
            String password = InputUtil.getString("请输入密码：");

            User user = userService.login(username, password);
            if (user != null) {
                currentUser = user;
                System.out.println("登录成功！欢迎 " + user.getUsername());
                return;
            }

            System.out.println("用户名或密码错误！您还有 " + (2 - attempts) + " 次尝试机会。");
        }

        System.out.println("登录失败，尝试次数过多，请稍后再试！");
        try {
            System.out.println("系统将暂停 5 秒钟后再允许登录。");
            TimeUnit.SECONDS.sleep(5); // 暂停5秒
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // 获取当前登录用户
    public User getCurrentUser() {
        return currentUser;
    }
}