package com.ChenDll.classroommanagement.classroom;

import java.util.List;

public class ClassroomService {
    private final ClassroomDao classroomDao = new ClassroomDao();

    // 获取所有课室
    public List<Classroom> getAllClassrooms() {
        return classroomDao.getAllClassrooms();
    }

    // 更新课室状态
    public boolean updateClassroomAvailability(int id, boolean isAvailable) {
        return classroomDao.updateClassroomAvailability(id, isAvailable);
    }
}