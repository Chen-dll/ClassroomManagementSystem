package com.ChenDll.classroommanagement.user;

import com.ChenDll.classroommanagement.constants.Role;
import com.ChenDll.classroommanagement.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    // 注册用户（插入数据库）
    public static int addUser(String username, String password, Role role, String studentId, String department, String idCard) {
        String sql = "INSERT INTO Users (username, password, role, student_id, department, id_card) VALUES (?, ?, ?, ?, ?, ?)";

        // 如果是管理员，学生信息可以为 null
        if (role == Role.ADMIN) {
            studentId = null;
            department = null;
            idCard = null;
        }

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, username);
            stmt.setString(2, hashPassword(password)); // 存储加密后的密码
            stmt.setString(3, role.name());
            stmt.setString(4, studentId);  // 如果是管理员，学生信息为 null
            stmt.setString(5, department); // 如果是管理员，学生信息为 null
            stmt.setString(6, idCard);     // 如果是管理员，学生信息为 null
            stmt.executeUpdate();

            // 获取自动生成的 ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // 失败返回 -1
    }


    // 登录验证
    public boolean loginUser(String username, String password) {
        String sql = "SELECT password, role FROM Users WHERE username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return verifyPassword(password, storedPassword); // 校验密码
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // 用户不存在或密码错误
    }


    // 根据用户名查找用户信息
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")),
                        rs.getString("student_id"),
                        rs.getString("department"),
                        rs.getString("id_card")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 使用 SHA-256 对密码加密
    private static String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(Integer.toHexString(0xff & b));
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return password; // 发生错误时返回原密码
        }
    }

    // 验证输入密码是否匹配加密存储的密码
    private boolean verifyPassword(String inputPassword, String storedPassword) {
        return hashPassword(inputPassword).equals(storedPassword);
    }

    // 获取所有用户信息
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")),
                        rs.getString("student_id"),
                        rs.getString("department"),
                        rs.getString("id_card")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // 删除用户
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM Users WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 检查用户名是否存在
    public static boolean checkUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // 如果查到用户，返回 true
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // 用户名不存在
    }
}
