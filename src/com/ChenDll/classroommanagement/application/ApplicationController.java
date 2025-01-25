package com.ChenDll.classroommanagement.application;

import com.ChenDll.classroommanagement.constants.AppConstants;
import com.ChenDll.classroommanagement.util.InputUtil;

public class ApplicationController {
    private final ApplicationService applicationService = new ApplicationService();

    // 提交申请（学生功能）
    public void submitApplication(int userId) {
        int classroomId = InputUtil.getInt("请输入课室ID：");
        String reason = InputUtil.getString("请输入申请理由：");
        applicationService.submitApplication(classroomId, userId, reason);
        System.out.println("申请提交成功，等待管理员审批！");
    }

    // 查看所有申请（管理员功能）
    public void viewAllApplications() {
        System.out.println("\n=== 所有申请 ===");
        for (Application application : applicationService.getAllApplications()) {
            System.out.println(application);
        }
    }

    // 审批申请（管理员功能）
    public void approveApplication() {
        int applicationId = InputUtil.getInt("请输入申请ID：");
        String status = InputUtil.getString("请输入审批状态（APPROVED/REJECTED）：");
        boolean success = applicationService.updateApplicationStatus(applicationId, status);
        System.out.println(success ? "审批成功！" : "审批失败，申请ID不存在！");
    }
}