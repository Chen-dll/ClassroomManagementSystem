package com.ChenDll.classroommanagement.constants;

// 用户角色枚举
public enum Role {
    ADMIN("ADMIN"),
    STUDENT("STUDENT");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
