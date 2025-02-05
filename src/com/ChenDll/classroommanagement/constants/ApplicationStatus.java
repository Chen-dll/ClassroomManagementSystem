package com.ChenDll.classroommanagement.constants;

// 申请状态枚举
public enum ApplicationStatus {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    EXPIRED("EXPIRED");

    private final String status;

    ApplicationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
