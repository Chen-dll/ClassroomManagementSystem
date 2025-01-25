package com.ChenDll.classroommanagement.application;

import com.ChenDll.classroommanagement.constants.AppConstants;

import java.util.List;

public class ApplicationService {
    private final ApplicationDao applicationDao = new ApplicationDao();

    // 提交申请
    public void submitApplication(int classroomId, int userId, String reason) {
        int newId = applicationDao.getAllApplications().size() + 1; // 模拟数据库自增ID
        Application application = new Application(newId, classroomId, userId, reason, AppConstants.STATUS_PENDING);
        applicationDao.addApplication(application);
    }

    // 获取所有申请
    public List<Application> getAllApplications() {
        return applicationDao.getAllApplications();
    }

    // 更新申请状态
    public boolean updateApplicationStatus(int applicationId, String status) {
        return applicationDao.updateApplicationStatus(applicationId, status);
    }
}