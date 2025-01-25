package com.ChenDll.classroommanagement.classroom;

import java.util.ArrayList;
import java.util.List;

public class ClassroomDao {
    private final List<Classroom> classrooms = new ArrayList<>(); // 模拟课室数据库

    public ClassroomDao() {
        // 初始化一些课室数据
        classrooms.add(new Classroom(1, "101教室", 50, true, true));
        classrooms.add(new Classroom(2, "102教室", 100, false, true));
        classrooms.add(new Classroom(3, "103教室", 30, true, false));
    }

    // 获取所有课室
    public List<Classroom> getAllClassrooms() {
        return classrooms;
    }

    // 根据课室ID查找课室
    public Classroom getClassroomById(int id) {
        for (Classroom classroom : classrooms) {
            if (classroom.getId() == id) {
                return classroom;
            }
        }
        return null;
    }

    // 更新课室状态
    public boolean updateClassroomAvailability(int id, boolean isAvailable) {
        Classroom classroom = getClassroomById(id);
        if (classroom != null) {
            classroom.setAvailable(isAvailable);
            return true;
        }
        return false;
    }
}