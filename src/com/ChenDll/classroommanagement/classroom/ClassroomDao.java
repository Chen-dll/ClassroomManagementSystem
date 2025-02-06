package com.ChenDll.classroommanagement.classroom;

import com.ChenDll.classroommanagement.util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClassroomDao {

    // 获取所有课室
    public List<Classroom> getAllClassrooms() {
        String sql = "SELECT * FROM Classrooms";
        List<Classroom> classrooms = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                classrooms.add(new Classroom(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("capacity"),
                        rs.getBoolean("has_multimedia"),
                        rs.getBoolean("is_available")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classrooms;
    }

    // 获取指定ID的课室
    public Classroom getClassroomById(int id) {
        String sql = "SELECT * FROM Classrooms WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Classroom(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("capacity"),
                            rs.getBoolean("has_multimedia"),
                            rs.getBoolean("is_available")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 添加课室
    public boolean addClassroom(Classroom classroom) {
        String sql = "INSERT INTO Classrooms (name, capacity, has_multimedia, is_available) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, classroom.getName());
            ps.setInt(2, classroom.getCapacity());
            ps.setBoolean(3, classroom.isHasMultimedia());
            ps.setBoolean(4, classroom.isAvailable());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    // 更新课室信息（改全部）
//    public boolean updateClassroom(Classroom classroom) {
//        String sql = "UPDATE Classrooms SET name = ?, capacity = ?, has_multimedia = ?, is_available = ? WHERE id = ?";
//        try (Connection conn = DBUtil.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, classroom.getName());
//            ps.setInt(2, classroom.getCapacity());
//            ps.setBoolean(3, classroom.isHasMultimedia());
//            ps.setBoolean(4, classroom.isAvailable());
//            ps.setInt(5, classroom.getId());
//            return ps.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    // 更新课室状态
    public boolean updateClassroomAvailability(int id, boolean isAvailable) {
        String sql = "UPDATE Classrooms SET is_available = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, isAvailable);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 获取特定时间段内已被占用的课室数量
    public static int getOccupiedClassroomCount(LocalDateTime startTime, LocalDateTime endTime) {
        String sql = "SELECT COUNT(DISTINCT classroom_id) FROM Applications WHERE status = 'APPROVED' " +
                "AND ((start_time <= ? AND end_time >= ?) OR (start_time >= ? AND start_time < ?))";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(endTime));
            ps.setTimestamp(2, Timestamp.valueOf(startTime));
            ps.setTimestamp(3, Timestamp.valueOf(startTime));
            ps.setTimestamp(4, Timestamp.valueOf(endTime));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 获取总课室数量
    public static int getTotalClassroomCount() {
        String sql = "SELECT COUNT(*) FROM Classrooms";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

//    // 获取按类型统计的课室占用数量
//        public static Map<String, Integer> getUsageByClassroomType(LocalDateTime startTime, LocalDateTime endTime) {
//        String sql = "SELECT c.type, COUNT(DISTINCT a.classroom_id) AS count FROM Applications a " +
//                "JOIN Classrooms c ON a.classroom_id = c.id WHERE a.status = 'APPROVED' " +
//                "AND ((a.start_time <= ? AND a.end_time >= ?) OR (a.start_time >= ? AND a.start_time < ?)) " +
//                "GROUP BY c.type";
//        Map<String, Integer> usageByType = new HashMap<>();
//        try (Connection conn = DBUtil.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setTimestamp(1, Timestamp.valueOf(endTime));
//            ps.setTimestamp(2, Timestamp.valueOf(startTime));
//            ps.setTimestamp(3, Timestamp.valueOf(startTime));
//            ps.setTimestamp(4, Timestamp.valueOf(endTime));
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    usageByType.put(rs.getString("type"), rs.getInt("count"));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return usageByType;
//    }

    // 更新课室的活动安排（修改课室的时间段）
    public boolean updateClassroomSchedule(int classroomId, LocalDateTime startTime, LocalDateTime endTime) {
        String sql = "UPDATE Applications SET start_time = ?, end_time = ? WHERE classroom_id = ? AND status = 'APPROVED'";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(startTime));
            ps.setTimestamp(2, Timestamp.valueOf(endTime));
            ps.setInt(3, classroomId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 删除课室
    public boolean deleteClassroom(int classroomId) {
        String sql = "DELETE FROM Classrooms WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, classroomId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;  // 返回是否删除成功
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}