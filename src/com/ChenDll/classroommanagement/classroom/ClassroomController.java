package com.ChenDll.classroommanagement.classroom;

import com.ChenDll.classroommanagement.application.ApplicationService;
import com.ChenDll.classroommanagement.constants.ApplicationStatus;
import com.ChenDll.classroommanagement.util.InputUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ClassroomController {
    private final ClassroomService classroomService = new ClassroomService();
    private final ApplicationService applicationService = new ApplicationService();
    // 查看所有课室信息
    public void viewAllClassrooms() {
        System.out.println("\n=== 所有课室信息 ===");
        for (Classroom classroom : classroomService.getAllClassrooms()) {
            System.out.println(classroom);
        }
    }

    // 修改课室状态（管理员权限）
    public void updateClassroomAvailability() {
        int classroomId = InputUtil.getInt("请输入课室ID：");
        boolean isAvailable = InputUtil.getString("请输入课室状态（true:可用 / false:不可用）：").equalsIgnoreCase("true");
        boolean success = classroomService.updateClassroomAvailability(classroomId, isAvailable);
        System.out.println(success ? "课室状态更新成功！" : "课室状态更新失败，课室ID不存在！");
    }

    // 添加新课室信息
    public void addClassroom() {
        String name = InputUtil.getString("请输入课室名称：");
        int capacity = InputUtil.getInt("请输入课室容量：");
        boolean hasMultimedia = InputUtil.getString("是否有多媒体设备（true/false）：").equalsIgnoreCase("true");
        boolean isAvailable = InputUtil.getString("课室是否可用（true/false）：").equalsIgnoreCase("true");

        boolean success = classroomService.addClassroom(name, capacity, hasMultimedia, isAvailable);
        if (success) {
            System.out.println("新课室信息添加成功！");
        } else {
            System.out.println("添加课室失败，请重试！");
        }
    }

    // 统计课室使用情况
    public void displayClassroomUsage() {
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime startTime = null;
        LocalDateTime endTime = null;

        // 获取并解析开始时间，直到格式正确
        while (startTime == null) {
            String startTimeStr = InputUtil.getString("请输入开始时间（格式：yyyy-MM-dd HH:mm）：");
            try {
                startTime = LocalDateTime.parse(startTimeStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("❌ 开始时间格式无效，请确保格式为 yyyy-MM-dd HH:mm");
            }
        }

        // 获取并解析结束时间，直到格式正确
        while (endTime == null) {
            String endTimeStr = InputUtil.getString("请输入结束时间（格式：yyyy-MM-dd HH:mm）：");
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
        double usageRate = classroomService.getClassroomUsageRate(startTime, endTime);
        System.out.println("课室使用率: " + usageRate + "%");
    }

    // 修改课室的活动安排（管理员权限）
    public void updateClassroomSchedule() {
        int classroomId = InputUtil.getInt("请输入课室ID：");
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime startTime = null;
        LocalDateTime endTime = null;

        // 获取并解析开始时间，直到格式正确
        while (startTime == null) {
            String startTimeStr = InputUtil.getString("请输入开始时间（格式：yyyy-MM-dd HH:mm）：");
            try {
                startTime = LocalDateTime.parse(startTimeStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("❌ 开始时间格式无效，请确保格式为 yyyy-MM-dd HH:mm");
            }
        }

        // 获取并解析结束时间，直到格式正确
        while (endTime == null) {
            String endTimeStr = InputUtil.getString("请输入结束时间（格式：yyyy-MM-dd HH:mm）：");
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

        // 检查新的时间段是否有冲突
        boolean isAvailable = applicationService.isTimeAvailable(classroomId, startTime, endTime);

        if (isAvailable) {
            // 修改课室活动安排
            boolean success = classroomService.updateClassroomSchedule(classroomId, startTime, endTime);
            if (success) {
                System.out.println("课室活动安排已成功更新！");
            } else {
                System.out.println("更新活动安排失败！");
            }
        } else {
            System.out.println("该课室在此时间段已有安排，无法修改！");
        }
    }

    // 删除课室信息（管理员权限）
    public void deleteClassroom(ClassroomController classroomController) {
        int classroomId = InputUtil.getInt("请输入要删除的课室ID：");

        // 检查课室是否存在
        if (!classroomController.isClassroomExists(classroomId)) {
            System.out.println("❌ 该课室不存在！");
            return;
        }

        // 检查课室是否有正在进行的申请或活动
        if (classroomController.isClassroomInUse(classroomId)) {
            System.out.println("❌ 该课室正在使用或有申请，无法删除！");
            return;
        }

        // 确认删除
        String confirmation = InputUtil.getString("确定删除该课室吗？(Y/N)：").toUpperCase();
        if (confirmation.equals("Y")) {
            boolean success = classroomService.deleteClassroomFromDatabase(classroomId);
            if (success) {
                System.out.println("课室删除成功！");
            } else {
                System.out.println("❌ 删除课室失败！");
            }
        } else {
            System.out.println("删除操作已取消！");
        }
    }


    // 检查课室是否存在
    public boolean isClassroomExists(int classroomId) {
        return classroomService.getClassroomById(classroomId) != null;
    }

    // 检查课室是否正在使用或有申请
    public boolean isClassroomInUse(int classroomId) {
        // 检查是否有任何正在进行的申请或活动
        return applicationService.getApplicationsByClassroom(classroomId).stream()
                .anyMatch(application -> application.getStatus() == ApplicationStatus.PENDING ||
                        application.getStatus() == ApplicationStatus.APPROVED);
    }
}
