package com.ChenDll.classroommanagement.user;

import com.ChenDll.classroommanagement.constants.Role;  // 引用独立的 Role 枚举

import java.time.LocalDateTime;

public class User {
    private int id;               // 用户ID
    private String username;      // 用户名
    private String password;      // 密码
    private Role role;            // 用户角色（ADMIN/STUDENT）
    private String studentId;     // 学号
    private String department;    // 院系
    private String idCard;        // 身份证号
    private int failedAttempts;   // 登录失败次数
    private LocalDateTime lockUntil; // 锁定到期时间

    // 构造方法
    public User(int id, String username, String password, Role role, String studentId, String department, String idCard) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.studentId = studentId;
        this.department = department;
        this.idCard = idCard;
    }

    // Getter 和 Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getIdCard() { return idCard; }
    public void setIdCard(String idCard) { this.idCard = idCard; }

    public int getFailedAttempts() { return failedAttempts; }
    public void setFailedAttempts(int failedAttempts) { this.failedAttempts = failedAttempts; }

    public LocalDateTime getLockUntil() { return lockUntil; }
    public void setLockUntil(LocalDateTime lockUntil) { this.lockUntil = lockUntil; }

    public boolean isLocked() {
        return lockUntil != null && lockUntil.isAfter(LocalDateTime.now());
    }

    public void lockUser() {
        this.lockUntil = LocalDateTime.now().plusMinutes(10); // 锁定10分钟
    }

    // 暂时没用到
    @Override
    public String toString() {
        return String.format("用户ID：%d\n用户名：%s\n角色：%s\n学号：%s\n院系：%s\n身份证号：%s\n登录失败次数：%d\n锁定到期时间：%s\n",
                id,
                username,
                role,
                studentId,
                department,
                idCard,
                failedAttempts,
                (lockUntil != null ? lockUntil.toString() : "无"));
    }
}
