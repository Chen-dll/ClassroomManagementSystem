package com.ChenDll.classroommanagement.application;

import com.ChenDll.classroommanagement.constants.ApplicationStatus;
import com.ChenDll.classroommanagement.util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDao {

    // 获取所有申请
    public List<Application> getAllApplications() {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT * FROM Applications WHERE status != 'EXPIRED'";  // 排除过期的申请

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                applications.add(mapRowToApplication(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }


    // 获取单个申请 by ID
    public Application getApplicationById(int applicationId) {
        String sql = "SELECT * FROM Applications WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, applicationId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToApplication(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 更新申请状态
    public void updateApplicationStatus(int applicationId, ApplicationStatus status) {
        String sql = "UPDATE Applications SET status = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status.name());
            pstmt.setInt(2, applicationId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 更新申请状态为已拒绝
    public void updateApplicationStatus(int classroomId, int userId, ApplicationStatus status) {
        String sql = "UPDATE Applications SET status = ? WHERE classroom_id = ? AND student_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setInt(2, classroomId);
            ps.setInt(3, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 检查时间冲突
    public boolean isTimeAvailable(int classroomId, LocalDateTime startTime, LocalDateTime endTime) {
        String sql = "SELECT COUNT(*) FROM Applications WHERE classroom_id = ? AND (start_time < ? AND end_time > ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, classroomId);
            ps.setTimestamp(2, Timestamp.valueOf(endTime));
            ps.setTimestamp(3, Timestamp.valueOf(startTime));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return false; // 存在冲突
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true; // 无冲突
    }

    // 添加申请记录
    public void addApplication(Application application) {
        String sql = "INSERT INTO Applications (student_id, classroom_id, reason, status, start_time, end_time, created_time, expire_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, application.getUserId());
            ps.setInt(2, application.getClassroomId());
            ps.setString(3, application.getReason());
            ps.setString(4, application.getStatus().name());
            ps.setTimestamp(5, Timestamp.valueOf(application.getStartTime()));
            ps.setTimestamp(6, Timestamp.valueOf(application.getEndTime()));
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now())); // 当前时间为创建时间
            ps.setTimestamp(8, Timestamp.valueOf(application.getExpireTime()));  // 过期时间
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 获取学生当前有效申请数量
    public int getActiveApplicationCount(int studentId) {
        String sql = "SELECT COUNT(*) FROM Applications WHERE student_id = ? AND status IN ('PENDING', 'APPROVED')";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
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

    // 获取教室的申请记录
    public List<Application> getApplicationsByClassroom(int classroomId) {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT * FROM Applications WHERE classroom_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, classroomId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                applications.add(mapRowToApplication(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }

    // 将 ResultSet 中的一行数据转换为 Application 对象
    private Application mapRowToApplication(ResultSet rs) throws SQLException {
        return new Application(
                rs.getInt("id"),
                rs.getInt("student_id"),
                rs.getInt("classroom_id"),
                rs.getString("reason"),
                ApplicationStatus.valueOf(rs.getString("status")),  // 使用 ApplicationStatus 枚举
                rs.getTimestamp("start_time") != null ? rs.getTimestamp("start_time").toLocalDateTime() : null,
                rs.getTimestamp("end_time") != null ? rs.getTimestamp("end_time").toLocalDateTime() : null,
                rs.getTimestamp("expire_time") != null ? rs.getTimestamp("expire_time").toLocalDateTime() : null
        );
    }
}
