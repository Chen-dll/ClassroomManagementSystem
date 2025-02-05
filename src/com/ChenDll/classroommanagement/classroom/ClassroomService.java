package com.ChenDll.classroommanagement.classroom;

import java.time.LocalDateTime;
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

    // 添加新课室
    public boolean addClassroom(String name, int capacity, boolean hasMultimedia, boolean isAvailable) {
        Classroom classroom = new Classroom();
        classroom.setName(name);
        classroom.setCapacity(capacity);
        classroom.setHasMultimedia(hasMultimedia);
        classroom.setAvailable(isAvailable);

        return classroomDao.addClassroom(classroom);
    }

    // 统计时间段内的课室使用率
    public double getClassroomUsageRate(LocalDateTime startTime, LocalDateTime endTime) {
        int occupied = ClassroomDao.getOccupiedClassroomCount(startTime, endTime);
        int total = ClassroomDao.getTotalClassroomCount();
        if (total == 0) {
            System.out.println("无可用课室！");
            return 0.0;
        }
        return (double) occupied / total * 100;
    }

//    // 统计时间段内各类型课室的使用情况
//    public Map<String, Integer> getClassroomUsageByType(LocalDateTime startTime, LocalDateTime endTime) {
//        return ClassroomDao.getUsageByClassroomType(startTime, endTime);
//    }

    // 修改课室活动安排
    public boolean updateClassroomSchedule(int classroomId, LocalDateTime startTime, LocalDateTime endTime) {
        // 更新课室的活动安排信息
        return classroomDao.updateClassroomSchedule(classroomId, startTime, endTime);
    }

    // 检查课室是否可用
    public boolean isClassroomAvailable(int classroomId) {
        Classroom classroom = classroomDao.getClassroomById(classroomId);
        return classroom != null && classroom.isAvailable();  // 如果课室存在且可用，则返回 true
    }

    // 删除课室
    public boolean deleteClassroomFromDatabase(int classroomId) {
        return classroomDao.deleteClassroom(classroomId);
    }

    // 获取课室信息
    public Classroom getClassroomById(int classroomId) {
        return classroomDao.getClassroomById(classroomId);
    }
}
