package com.ChenDll.classroommanagement;

import com.ChenDll.classroommanagement.application.ApplicationController;
import com.ChenDll.classroommanagement.classroom.ClassroomController;
import com.ChenDll.classroommanagement.user.UserController;
import com.ChenDll.classroommanagement.util.InputUtil;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        UserController userController = new UserController();
        ClassroomController classroomController = new ClassroomController();
        ApplicationController applicationController = new ApplicationController();

        while (true) {
            System.out.println("\n欢迎使用课室管理系统（当前时间：" + LocalDateTime.now() + "）");
            System.out.println("\n=== 课室管理系统 ===");
            System.out.println("如果忘记密码，请联系管理员重置！");
            System.out.println("1. 注册");
            System.out.println("2. 登录");
            System.out.println("3. 查看所有课室");
            System.out.println("4. 修改课室状态（管理员）");
            System.out.println("5. 提交课室申请（学生）");
            System.out.println("6. 查看所有申请（管理员）");
            System.out.println("7. 审批申请（管理员）");
            System.out.println("8. 退出");
            int choice = InputUtil.getInt("请选择操作：");

            switch (choice) {
                case 1 -> userController.register();
                case 2 -> userController.login();
                case 3 -> classroomController.viewAllClassrooms();
                case 4 -> {
                    if (userController.getCurrentUser() != null &&
                            "ADMIN".equalsIgnoreCase(userController.getCurrentUser().getRole())) {
                        classroomController.updateClassroomAvailability();
                    } else {
                        System.out.println("您没有权限执行此操作！");
                    }
                }
                case 5 -> {
                    if (userController.getCurrentUser() != null &&
                            "STUDENT".equalsIgnoreCase(userController.getCurrentUser().getRole())) {
                        applicationController.submitApplication(userController.getCurrentUser().getId());
                    } else {
                        System.out.println("您没有权限执行此操作！");
                    }
                }
                case 6 -> {
                    if (userController.getCurrentUser() != null &&
                            "ADMIN".equalsIgnoreCase(userController.getCurrentUser().getRole())) {
                        applicationController.viewAllApplications();
                    } else {
                        System.out.println("您没有权限执行此操作！");
                    }
                }
                case 7 -> {
                    if (userController.getCurrentUser() != null &&
                            "ADMIN".equalsIgnoreCase(userController.getCurrentUser().getRole())) {
                        applicationController.approveApplication();
                    } else {
                        System.out.println("您没有权限执行此操作！");
                    }
                }
                case 8 -> {
                    System.out.println("退出系统。");
                    return;
                }
                default -> System.out.println("无效选择，请重试！");
            }
        }
    }
}