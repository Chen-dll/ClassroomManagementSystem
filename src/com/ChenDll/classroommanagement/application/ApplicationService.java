package com.ChenDll.classroommanagement.application;

import com.ChenDll.classroommanagement.constants.ApplicationStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ApplicationService {
    private final ApplicationDao applicationDao = new ApplicationDao();
    public static final int MAX_APPLICATIONS = 3;

    // 提交申请
    public void submitApplication(int classroomId, int userId, String reason, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime expireTime) {
        Application application = new Application(
                0, classroomId, userId, reason, ApplicationStatus.PENDING, startTime, endTime, expireTime
        );
        applicationDao.addApplication(application);
    }

    // 获取所有申请
    public List<Application> getAllApplications() {
        return applicationDao.getAllApplications();
    }

    // 检查申请时间是否冲突
    public boolean isTimeAvailable(int classroomId, LocalDateTime startTime, LocalDateTime endTime) {
        return applicationDao.isTimeAvailable(classroomId, startTime, endTime);
    }

    // 获取当前学生有效申请数量
    public int getActiveApplicationCount(int studentId) {
        return applicationDao.getActiveApplicationCount(studentId);
    }

    // 获取申请 by ID
    public Application getApplicationById(int applicationId) {
        return applicationDao.getApplicationById(applicationId);
    }

    // 更新申请状态
    public void updateApplicationStatus(int applicationId, ApplicationStatus status) {
        applicationDao.updateApplicationStatus(applicationId, status);
    }

    // 拒绝申请
    public void rejectApplication(int classroomId, int userId) {
        applicationDao.updateApplicationStatus(classroomId, userId, ApplicationStatus.REJECTED);
    }

    // 获取教室的申请
    public List<Application> getApplicationsByClassroom(int classroomId) {
        return applicationDao.getApplicationsByClassroom(classroomId);
    }
}
