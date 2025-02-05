package com.ChenDll.classroommanagement.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private static final HikariDataSource dataSource;

    static {
        // 禁用 HikariCP 的调试日志
        System.setProperty("com.zaxxer.hikari.pool.HikariPool", "false");  // 关闭连接池的调试日志输出
        System.setProperty("java.util.logging.ConsoleHandler.level", "WARNING");
        System.setProperty("com.zaxxer.hikari.level", "WARN");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/classroom_management");
        config.setUsername("root");
        config.setPassword("Ch1234");
        config.setMaximumPoolSize(10);  // 最大连接池大小
        config.setMinimumIdle(5);  // 最小空闲连接
        config.setIdleTimeout(30000);  // 空闲连接超时时间
        config.setConnectionTimeout(30000);  // 连接超时
        config.setConnectionTestQuery("SELECT 1");  // 测试连接查询

        // 禁用连接泄漏检测（如果不需要）
        config.setLeakDetectionThreshold(0);

        // 初始化连接池
        dataSource = new HikariDataSource(config);
    }

    // 获取数据库连接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // 关闭数据库连接
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("关闭数据库连接失败: " + e.getMessage());
            }
        }
    }
}
