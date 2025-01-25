package com.ChenDll.classroommanagement.classroom;

import com.ChenDll.classroommanagement.util.InputUtil;

public class ClassroomController {
    private final ClassroomService classroomService = new ClassroomService();

    // 查看所有课室信息
    public void viewAllClassrooms() {
        System.out.println("\n=== 所有课室信息 ===");
        for (Classroom classroom : classroomService.getAllClassrooms()) {
            System.out.println(classroom);
        }
    }

    // 动态修改课室状态（管理员权限）
    public void updateClassroomAvailability() {
        int classroomId = InputUtil.getInt("请输入课室ID：");
        boolean isAvailable = InputUtil.getString("请输入课室状态（true:可用 / false:不可用）：").equalsIgnoreCase("true");
        boolean success = classroomService.updateClassroomAvailability(classroomId, isAvailable);
        System.out.println(success ? "课室状态更新成功！" : "课室状态更新失败，课室ID不存在！");
    }
}