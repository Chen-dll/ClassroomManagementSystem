package com.ChenDll.classroommanagement.application;

import java.util.ArrayList;
import java.util.List;

public class ApplicationDao {
    private final List<Application> applications = new ArrayList<>(); // 模拟申请数据库

    // 添加申请
    public void addApplication(Application application) {
        applications.add(application);
    }

    // 获取所有申请
    public List<Application> getAllApplications() {
        return applications;
    }

    // 根据状态筛选申请
    public List<Application> getApplicationsByStatus(String status) {
        List<Application> result = new ArrayList<>();
        for (Application application : applications) {
            if (application.getStatus().equals(status)) {
                result.add(application);
            }
        }
        return result;
    }

    // 更新申请状态
    public boolean updateApplicationStatus(int applicationId, String status) {
        for (Application application : applications) {
            if (application.getId() == applicationId) {
                application.setStatus(status);
                return true;
            }
        }
        return false;
    }
}