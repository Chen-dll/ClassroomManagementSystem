package com.ChenDll.classroommanagement.application;

public class Application {
    private int id;               // 申请ID
    private int classroomId;      // 课室ID
    private int userId;           // 学生用户ID
    private String reason;        // 申请理由
    private String status;        // 申请状态（PENDING/APPROVED/REJECTED）

    public Application(int id, int classroomId, int userId, String reason, String status) {
        this.id = id;
        this.classroomId = classroomId;
        this.userId = userId;
        this.reason = reason;
        this.status = status;
    }

    // Getter 和 Setter
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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", classroomId=" + classroomId +
                ", userId=" + userId +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}