package com.ChenDll.classroommanagement.application;

import com.ChenDll.classroommanagement.classroom.ClassroomService;
import com.ChenDll.classroommanagement.constants.ApplicationStatus;
import com.ChenDll.classroommanagement.util.InputUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ApplicationController {
    private final ApplicationService applicationService = new ApplicationService();
    private final ClassroomService classroomService = new ClassroomService();
    // 提交申请（学生功能）
    public void submitApplication(int userId) {
        int classroomId = InputUtil.getInt("请输入课室ID：");
        String reason = InputUtil.getString("请输入申请理由：");
//        String startTimeStr = InputUtil.getString("请输入申请开始时间（格式：yyyy-MM-dd HH:mm）：");
//        String endTimeStr = InputUtil.getString("请输入申请结束时间（格式：yyyy-MM-dd HH:mm）：");

//        LocalDateTime startTime = LocalDateTime.parse(startTimeStr);  // 解析为LocalDateTime
//        LocalDateTime endTime = LocalDateTime.parse(endTimeStr);

        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime startTime = null;
        LocalDateTime endTime = null;

        // 获取并解析开始时间，直到格式正确
        while (startTime == null) {
            String startTimeStr = InputUtil.getString("请输入申请开始时间（格式：yyyy-MM-dd HH:mm）：");
            try {
                startTime = LocalDateTime.parse(startTimeStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("❌ 开始时间格式无效，请确保格式为 yyyy-MM-dd HH:mm");
            }
        }

        // 获取并解析结束时间，直到格式正确
        while (endTime == null) {
            String endTimeStr = InputUtil.getString("请输入申请结束时间（格式：yyyy-MM-dd HH:mm）：");
            try {
                endTime = LocalDateTime.parse(endTimeStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("❌ 结束时间格式无效，请确保格式为 yyyy-MM-dd HH:mm");
            }
        }

        // 检查结束时间是否早于开始时间
        if (endTime.isBefore(startTime)) {
            System.out.println("❌ 结束时间不能早于开始时间！");
            return;  // 如果结束时间早于开始时间，直接返回，拒绝申请
        }

        // 获取当前时间，并计算有效期（3天）
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expireTime = currentTime.plusDays(3);  // 设置有效期为当前时间后的3天

        // 检查学生的申请数量是否超出限制
        if (applicationService.getActiveApplicationCount(userId) >= ApplicationService.MAX_APPLICATIONS) {
            System.out.println("❌ 您的申请数量已达上限！");
            return;
        }

        // 检查课室状态（是否可用）
        if (!classroomService.isClassroomAvailable(classroomId)) {
            System.out.println("❌ 该课室当前不可用，您的申请已被自动驳回！");
            applicationService.rejectApplication(classroomId, userId);  // 自动驳回申请
            return;
        }

        // 检查课室时间冲突
        if (!applicationService.isTimeAvailable(classroomId, startTime, endTime)) {
            System.out.println("❌ 该课室在该时间段内不可用！");
            return;
        }

        // 提交申请并记录过期时间
        applicationService.submitApplication(classroomId, userId, reason, startTime, endTime, expireTime);
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
        int applicationId = InputUtil.getInt("请输入要审批的申请ID：");

        // 获取申请
        Application application = applicationService.getApplicationById(applicationId);

        if (application == null) {
            System.out.println("❌ 申请ID无效！");
            return;
        }

        // 检查申请状态是否为待审批
        if (application.getStatus() != ApplicationStatus.PENDING) {
            System.out.println("❌ 该申请已被处理，无法再次审批！");
            return;  // 如果申请已被处理（已批准或已拒绝），则不允许继续审批
        }

        // 显示申请详情
        System.out.println("申请详情：");
        System.out.println(application);

        // 确认是否审批
        String decision = InputUtil.getString("请输入审批决定（approve/reject）：");

        if (decision.equalsIgnoreCase("approve")) {
            applicationService.updateApplicationStatus(applicationId, ApplicationStatus.APPROVED);
            System.out.println("✔️ 申请已批准！");
        } else if (decision.equalsIgnoreCase("reject")) {
            applicationService.updateApplicationStatus(applicationId, ApplicationStatus.REJECTED);
            System.out.println("❌ 申请已拒绝！");
        } else {
            System.out.println("❌ 无效的审批决定！");
        }
    }
}
