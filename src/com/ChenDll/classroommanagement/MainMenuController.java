package com.ChenDll.classroommanagement;

import com.ChenDll.classroommanagement.application.ApplicationController;
import com.ChenDll.classroommanagement.classroom.ClassroomController;
import com.ChenDll.classroommanagement.constants.Role;
import com.ChenDll.classroommanagement.user.UserController;
import com.ChenDll.classroommanagement.util.InputUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainMenuController {
    static final UserController userController = new UserController();
    static final ClassroomController classroomController = new ClassroomController();
    static final ApplicationController applicationController = new ApplicationController();
//    // 私有构造函数防止外部实例化
//    private MainMenuController() {}
//
//    // 提供全局访问点
//    public static MainMenuController getInstance() {
//        return SingletonHolder.INSTANCE;
//    }
//
//    // 使用静态内部类实现单例模式
//    private static class SingletonHolder {
//        private static final MainMenuController INSTANCE = new MainMenuController();
//    }

    public static void handleMainMenu() {
        while (true) {
            displayWelcomeMessage();  // 显示欢迎信息

            int choice = InputUtil.getInt("请选择操作：");

            switch (choice) {
                case 1:
                    userController.register();  // 用户注册
                    break;
                case 2:
                    handleLogin();  // 用户登录
                    break;
                case 3:
                    System.out.println("退出系统，感谢您的使用！");
                    System.exit(0); // 退出程序 防止出bug
                    return;
                default:
                    System.out.println("无效选择，请重试！");
            }
        }
    }

    // 显示欢迎信息
    private static void displayWelcomeMessage() {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("\n欢迎使用课室管理系统（当前时间：" + now.format(formatter) + "）");
        System.out.println("\n=== 课室管理系统 ===");
        System.out.println("如果忘记密码，请联系管理员重置！");
        System.out.println("1. 注册");
        System.out.println("2. 登录");
        System.out.println("3. 退出");
    }

    // 处理用户登录后的操作
    private static void handleLogin() {
        MainMenuController.userController.login();

        if (MainMenuController.userController.getCurrentUser() != null) {
            Role userRole = MainMenuController.userController.getCurrentUser().getRole();  // 获取用户角色
            if (userRole == Role.ADMIN) {
                handleAdminMenu();
            } else if (userRole == Role.STUDENT) {
                handleStudentMenu();
            }
        } else {
            System.out.println("登录失败，请检查用户名或密码！");
        }
    }

    // 处理管理员菜单
    private static void handleAdminMenu() {
        System.out.println("欢迎管理员：" + MainMenuController.userController.getCurrentUser().getUsername() + " 登录！");

        boolean isAdminLoggedIn = true;
        while (isAdminLoggedIn) {
            displayAdminMenu();

            int adminChoice = InputUtil.getInt("请选择操作：");
            switch (adminChoice) {
                case 1:
                    MainMenuController.classroomController.viewAllClassrooms();
                    break;
                case 2:
                    MainMenuController.applicationController.viewAllApplications();
                    break;
                case 3:
                    MainMenuController.classroomController.addClassroom();
                    break;
                case 4:
                    MainMenuController.classroomController.deleteClassroom(MainMenuController.classroomController);
                    break;
                case 5:
                    MainMenuController.classroomController.updateClassroomAvailability();
                    break;
                case 6:
                    MainMenuController.applicationController.approveApplication();  // 审批申请
                    break;
                case 7:
                    MainMenuController.classroomController.displayClassroomUsage();  // 调用查看课室使用情况方法
                    break;
                case 8:
                    MainMenuController.classroomController.updateClassroomSchedule();  // 调用修改课室活动安排方法
                    break;
                case 9:
                    System.out.println("退出管理员菜单！");
                    isAdminLoggedIn = false;
                    break;
                default:
                    System.out.println("无效选择，请重新输入！");
            }
        }
    }

    // 处理学生菜单
    private static void handleStudentMenu() {
        System.out.println("欢迎学生：" + MainMenuController.userController.getCurrentUser().getUsername() + " 登录！");

        boolean isStudentLoggedIn = true;
        while (isStudentLoggedIn) {
            displayStudentMenu();

            int studentChoice = InputUtil.getInt("请选择操作：");
            switch (studentChoice) {
                case 1:
                    MainMenuController.classroomController.viewAllClassrooms();
                    break;
                case 2:
                    MainMenuController.applicationController.submitApplication(MainMenuController.userController.getCurrentUser().getId());
                    break;
                case 3:
                    MainMenuController.applicationController.viewAllApplications();
                    break;
                case 4:
                    System.out.println("退出学生菜单！");
                    isStudentLoggedIn = false;
                    break;
                default:
                    System.out.println("无效选择，请重新输入！");
            }
        }
    }

    // 显示管理员菜单
    private static void displayAdminMenu() {
        System.out.println("\n=== 管理员菜单 ===");
        System.out.println("1. 查看所有课室");
        System.out.println("2. 查看所有申请记录");
        System.out.println("3. 添加新课室");
        System.out.println("4. 删除课室");
        System.out.println("5. 修改课室状态");
        System.out.println("6. 审批申请");
        System.out.println("7. 统计课室使用情况");
        System.out.println("8. 修改课室活动安排");
        System.out.println("9. 退出登录");
    }

    // 显示学生菜单
    private static void displayStudentMenu() {
        System.out.println("\n=== 学生菜单 ===");
        System.out.println("1. 查看所有课室");
        System.out.println("2. 提交课室申请");
        System.out.println("3. 查看已提交的申请记录");
        System.out.println("4. 退出登录");
    }
}
