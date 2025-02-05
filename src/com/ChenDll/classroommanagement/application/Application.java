package com.ChenDll.classroommanagement.application;

import com.ChenDll.classroommanagement.constants.ApplicationStatus; // 使用 ApplicationStatus 枚举

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Application {
    private int id;               // 申请ID
    private int classroomId;      // 课室ID
    private int userId;           // 学生用户ID
    private String reason;        // 申请理由
    private ApplicationStatus status; // 申请状态（PENDING/APPROVED/REJECTED）
    private LocalDateTime startTime; // 申请开始时间
    private LocalDateTime endTime;   // 申请结束时间
    private LocalDateTime expireTime; // 申请过期时间

    public Application(int id, int classroomId, int userId, String reason, ApplicationStatus status, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime expireTime) {
        this.id = id;
        this.classroomId = classroomId;
        this.userId = userId;
        this.reason = reason;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.expireTime = expireTime;
    }

    // Getter 和 Setter
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("申请ID：%d\n课室ID：%d\n用户ID：%d\n申请理由：'%s'\n申请状态：%s\n开始时间：%s\n结束时间：%s\n过期时间：%s\n",
                id,
                classroomId,
                userId,
                reason,
                getStatusInChinese(status),
                (startTime != null) ? startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "未设置",
                (endTime != null) ? endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "未设置",
                (expireTime != null) ? expireTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "未设置"
        );
    }

    // 将申请状态转换为中文
    private String getStatusInChinese(ApplicationStatus status) {
        return switch (status) {
            case PENDING -> "待审批";
            case APPROVED -> "已批准";
            case REJECTED -> "已拒绝";
            default -> "未知状态";
        };
    }
}
