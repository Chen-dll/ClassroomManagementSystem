package com.ChenDll.classroommanagement.user;

import com.ChenDll.classroommanagement.MainMenuController;
import com.ChenDll.classroommanagement.util.InputUtil;
import com.ChenDll.classroommanagement.constants.Role;  // 引用 Role 枚举
import java.util.concurrent.TimeUnit;

public class UserController {
    private final UserService userService = new UserService();
    private User currentUser; // 当前登录的用户
    // 注册用户
    public void register() {
        System.out.println("\n=== 用户注册 ===");
        String username = InputUtil.getString("请输入用户名：");
        String password = InputUtil.getString("请输入密码：");
        String roleStr = InputUtil.getString("请输入角色（STUDENT/ADMIN）：").toUpperCase();

        // 校验角色
        Role role = Role.valueOf(roleStr);  // 使用 Role 枚举

        String studentId = null;
        String department = null;
        String idCard = null;

        // 如果角色是学生，要求输入学生信息
        if (role == Role.STUDENT) {
            studentId = InputUtil.getString("请输入学号：");
            department = InputUtil.getString("请输入院系：");
            idCard = InputUtil.getString("请输入身份证号：");
        }

        // 注册用户
        if (UserService.registerUser(username, password, role, studentId, department, idCard)) {
            System.out.println("✅ 用户 " + username + " 注册成功！");
        } else {
            System.out.println("❌ 注册失败，用户名可能已存在！");
        }
    }

    // 登录用户（新增三次重试逻辑）
    public void login() {
        System.out.println("\n=== 用户登录 ===");

        for (int attempts = 0; attempts < 3; attempts++) {
            String username = InputUtil.getString("请输入用户名：");
            String password = InputUtil.getString("请输入密码：");
            boolean success = UserService.login(username, password);
            User user = userService.getUserRole(username);

            if (success) {
                System.out.println("✅ 用户 " + username + " 登录成功！");
                currentUser = user;
                return;
            } else {
                System.out.println("❌ 用户名或密码错误！您还有 " + (2 - attempts) + " 次尝试机会。");
            }
        }
        System.out.println("登录失败，尝试次数过多，请稍后再试！");
        try {
            System.out.println("系统将暂停 5 秒钟后再允许登录。");
            TimeUnit.SECONDS.sleep(5); // 暂停5秒
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // 重新进入主菜单
        MainMenuController.handleMainMenu();
    }

    // 获取当前登录的用户
    public User getCurrentUser() {
        if (currentUser == null) {
            // System.out.println("用户未登录！");
            return null;
        }
        return currentUser;
    }
}
