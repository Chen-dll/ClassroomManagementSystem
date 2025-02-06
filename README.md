---

# 课室管理系统 ![](https://komarev.com/ghpvc/?username=Chen-dll)
The program is coded by Chen Sixiang.   
完成时间 2025年2月6日 

## 项目概述

该教室管理系统旨在为学校或教育机构提供一个易于使用的平台，帮助管理员管理课室的使用情况，同时为学生提供便捷的课室申请功能。系统使用基于角色的访问控制，区分管理员和学生的操作权限，确保不同角色的用户能够执行相应的操作。系统的主要功能包括用户管理、课室管理、申请管理以及菜单系统。

## 项目架构设计

为确保系统的可维护性与可扩展性，采用了**MVC分层架构**设计。系统主要包括以下几个模块：

- **`user` 模块**：负责用户的注册、登录和权限控制。
- **`classroom` 模块**：管理课室信息，包括添加、删除和修改课室。
- **`application` 模块**：处理学生的课室申请，管理员的审批功能。
- **`util` 模块**：提供通用工具类，如日期时间处理和数据库连接管理。

每个模块有明确的职责，确保代码的清晰与模块化管理。

## 代码实现

### **1. 用户管理模块 (`user` 模块)**

- **`User.java`**：定义了系统中的用户实体类，包含用户名、密码、角色等信息，使用了枚举类 `Role` 来区分管理员和学生。
```java
public class User {
    private int id;               // 用户ID
    private String username;      // 用户名
    private String password;      // 密码
    private Role role;            // 用户角色（ADMIN/STUDENT）
    private String studentId;     // 学号
    private String department;    // 院系
    private String idCard;        // 身份证号
    private int failedAttempts;   // 登录失败次数
    private LocalDateTime lockUntil; // 锁定到期时间

    // 构造方法
    public User(int id, String username, String password, Role role, String studentId, String department, String idCard) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.studentId = studentId;
        this.department = department;
        this.idCard = idCard;
    }

    // 省略getter和setter

    // 重写 toString 方法，方便打印用户信息（暂时没用到）
    @Override
    public String toString() {
        return String.format(
                "用户信息 {\n" +
                        "  用户ID: %d,\n" +
                        "  用户名: '%s',\n" +
                        "  角色: %s,\n" +
                        "  学号: '%s',\n" +
                        "  院系: '%s',\n" +
                        "  身份证号: '%s',\n" +
                        "  登录失败次数: %d,\n" +
                        "  锁定到期时间: %s\n" +
                        "}",
                id, username, role, studentId, department, idCard, failedAttempts, (lockUntil != null ? lockUntil.toString() : "无")
        );
    }
}
```

- **`Role.java`**：定义了用户角色的枚举类，用于区分管理员和学生的权限。
```java
public enum Role {
    ADMIN("ADMIN"),
    STUDENT("STUDENT");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
```

该枚举类 `Role` 用于区分用户的角色，并在系统中进行权限控制。

- **`UserController.java`**：该类处理用户的注册与登录请求。

```java
public class UserController {
    private final UserService userService = new UserService();
    private User currentUser;

    public void register() {
        // 用户注册逻辑
    }

    public void login() {
        // 用户登录逻辑
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
```

用户注册和登录的核心逻辑在 `UserService` 类中实现，`UserController` 仅负责接收输入并调用相应的服务。

- **`UserService.java`**：负责用户的业务逻辑，如验证用户名密码等。

```java
public class UserService {
    public boolean registerUser(String username, String password, Role role, String studentId, String department, String idCard) {
        // 注册用户逻辑
    }

    public boolean login(String username, String password) {
        // 登录验证逻辑
    }

    public User getUserRole(String username){
        // 获取用户角色逻辑
    }

    public User getUserInfo(String username){
        // 获取所有用户逻辑 （暂时没用到）
    }

    public List<User> getAllUsers() {
        // 获取所有用户逻辑 （暂时没用到）
    }

    public boolean removeUser(int id) {
        // 删除用户逻辑 （暂时没用到）
    }
}
```

- **`UserDao.java`**：负责与数据库交互，执行用户数据的增删改查操作，还有对密码的加密解密操作。
```java
public class UserDao {
    public static int addUser(String username, String password, Role role, String studentId, String department, String idCard) {
        // 添加用户逻辑
    }

    public boolean loginUser(String username, String password) {
        // 登录验证逻辑
    }

    public User getUserByUsername(String username) {
        // 根据用户名获取用户信息
    }

    private static String hashPassword(String password) {
         // 使用 SHA-256 对密码加密   
    }

    private boolean verifyPassword(String inputPassword, String storedPassword) {
        // 验证密码逻辑
    }

    public static boolean checkUsernameExists(String username) {
        // 检查用户名是否存在
    }

    public List<User> getAllUsers() {
        // 获取所有用户逻辑 （暂时没用到）
    }

        public boolean deleteUser(int id) {
        // 删除用户逻辑 （暂时没用到）
    }
}
```

### **2. 课室管理模块 (`classroom` 模块)**

- **`Classroom.java`**：定义了课室实体类，包含课室ID、名称、容量、是否有多媒体设备等字段。

```java
public class Classroom {
    private int id;
    private String name;
    private int capacity;
    private boolean hasMultimedia;
    private boolean isAvailable;

    // 构造函数与 Getter/Setter省略

    // 重写 toString 方法，方便打印课室信息
    @Override
    public String toString() {
        return String.format("课室ID：%d\n课室名称：%s\n容量：%d人\n是否有多媒体设备：%s\n课室状态：%s\n",
                id,
                name,
                capacity,
                hasMultimedia ? "有" : "无",
                isAvailable ? "可用" : "不可用");
    }
}
```

课室实体类提供了课室的基本信息，以供后续的课室管理操作使用。

- **`ClassroomController.java`**：实现课室管理的控制逻辑，支持查看、添加和修改课室信息。

```java
public class ClassroomController {
    
    public void viewAllClassrooms() {
        // 查看所有课室信息
    }

    public void addClassroom() {
        // 添加课室信息
    }

    public void updateClassroomAvailability() {
        // 更新课室状态
    }

     public void displayClassroomUsage() {
        // 显示课室使用情况
    }

    public void updateClassroomSchedule() {
        // 更新课室活动安排信息
    }

    public void deleteClassroom(ClassroomController classroomController) {
        // 删除课室信息
    }

    public boolean isClassroomExists(int classroomId) {
        // 检查课室是否存在
    }

    public boolean isClassroomInUse(int classroomId) {
        // 检查课室是否在使用或有申请
    }
}
```

`ClassroomController` 负责处理来自用户的请求，调用 `ClassroomService` 进行具体的业务操作。

- **`ClassroomService.java`**：包含课室管理的业务逻辑，执行添加、删除、修改课室的操作。

```java
public class ClassroomService {
    public List<Classroom> getAllClassrooms(){
        // 获取所有课室信息
    }

    public boolean updateClassroomAvailability(int id, boolean isAvailable) {
        // 更新课室的可用状态
    }

    public boolean addClassroom(String name, int capacity, boolean hasMultimedia, boolean isAvailable) {
        // 添加新的课室
    }

    public double getClassroomUsageRate(LocalDateTime startTime, LocalDateTime endTime) {
        // 计算课室使用率
    }

    public boolean updateClassroomSchedule(int classroomId, LocalDateTime startTime, LocalDateTime endTime) {
        // 更新课室活动安排信息
    }

    public boolean isClassroomAvailable(int classroomId) {
        // 检查课室是否可用
    }

    public boolean deleteClassroomFromDatabase(int classroomId) {
        // 从数据库中删除课室信息
    }

    public Classroom getClassroomById(int classroomId) {
        // 根据课室ID获取课室信息
    }
}
```

`ClassroomService` 处理了业务逻辑层的课室相关操作，例如添加课室、修改课室可用状态等。

- **`ClassroomDao.java`**：负责与数据库交互，执行课室数据的增删改查操作。
```java
public class ClassroomDao {
    public List<Classroom> getAllClassrooms() {
        // 从数据库中获取所有课室信息
    }

    public Classroom getClassroomById(int id) {
        // 从数据库根据课室ID获取课室信息
    }

    public boolean addClassroom(Classroom classroom) {
        // 添加课室信息到数据库
    }

    public boolean updateClassroom(Classroom classroom) {
        // 更新课室信息到数据库（暂时没用到）
    }

    public boolean updateClassroomAvailability(int id, boolean isAvailable) {
        // 更新课室可用状态到数据库
    }

    public static int getOccupiedClassroomCount(LocalDateTime startTime, LocalDateTime endTime) {
        // 获取特定时间段内已被占用的课室数量
    }

    public static int getTotalClassroomCount() {
        // 获取所有课室数量
    }

    public boolean updateClassroomSchedule(int classroomId, LocalDateTime startTime, LocalDateTime endTime) {
        // 更新课室的活动安排（修改课室的时间段）
    }

    public boolean deleteClassroom(int classroomId) {
        // 从数据库中删除课室信息
    }
}
```

### **3. 申请管理模块 (`application` 模块)**

- **`Application.java`**：定义课室申请的实体类，保存申请ID、用户ID、课室ID、申请状态、开始时间等信息。使用了枚举类`ApplicationStatus`，表示申请状态。

```java
public class Application {
    private int id;               // 申请ID
    private int classroomId;      // 课室ID
    private int userId;           // 学生用户ID
    private String reason;        // 申请理由
    private ApplicationStatus status; // 申请状态（PENDING/APPROVED/REJECTED）
    private LocalDateTime startTime; // 申请开始时间
    private LocalDateTime endTime;   // 申请结束时间
    private LocalDateTime expireTime; // 申请过期时间

    // 省略构造方法，getter/setter方法
    
    // 重写toString方法，方便打印申请信息
    @Override
    public String toString() {
        return String.format("申请ID：%d\n课室ID：%d\n用户ID：%d\n申请理由：'%s'\n申请状态：%s\n开始时间：%s\n结束时间：%s\n过期时间：%s\n",
                id,
                classroomId,
                userId,
                reason,
                getStatusInChinese(status),
                (startTime != null) ? startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "未设置",
                (endTime != null) ? endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "未设置",
                (expireTime != null) ? expireTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "未设置"
        );
    }

    // 将申请状态转换为中文
    private String getStatusInChinese(ApplicationStatus status) {
        return switch (status) {
            case PENDING -> "待审批";
            case APPROVED -> "已批准";
            case REJECTED -> "已拒绝";
            default -> "未知状态";
        };
    }
}
```

`Application` 类封装了申请的基本数据结构，为后续的申请提交与审批提供支持。

- **`ApplicationStatus.java`**：定义申请状态的枚举类型，包括待审批、已批准、已拒绝和已过期四种状态。

```java
// 申请状态枚举
public enum ApplicationStatus {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    EXPIRED("EXPIRED");

    private final String status;

    ApplicationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
```
`ApplicationStatus` 枚举类型定义了四种状态，分别表示待审批、已批准、已拒绝和已过期。

- **`ApplicationController.java`**：处理学生提交申请与管理员审批的逻辑。

```java
public class ApplicationController {
    public void submitApplication(int userId) {
         // 提交申请的业务逻辑
    }

    public void viewAllApplications() {
        // 查看所有申请的业务逻辑
    }

    public void approveApplication() {
        // 审批申请的业务逻辑
    }
}
```

`ApplicationController` 类接收用户输入，提交课室申请，或由管理员进行审批。

- **`ApplicationService.java`**：负责申请管理的核心业务逻辑，如检查时间是否冲突、申请状态更新等。

```java
public class ApplicationService {
    public void submitApplication(int classroomId, int userId, String reason, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime expireTime) {
        // 提交申请的业务逻辑
    }

    public List<Application> getAllApplications() {
        // 获取所有申请的业务逻辑
    }
   
   public boolean isTimeAvailable(int classroomId, LocalDateTime startTime, LocalDateTime endTime) {
        // 检查申请的时间是否与现有安排冲突
    }

    public int getActiveApplicationCount(int studentId) {
        // 获取当前用户所有未过期的申请数量
    }

    public Application getApplicationById(int applicationId) {
        // 根据申请ID获取申请信息
    }

    public void updateApplicationStatus(int applicationId, ApplicationStatus status) {
        // 更新申请状态
    }

    public void rejectApplication(int classroomId, int userId) {
        // 拒绝申请
    }

    public List<Application> getApplicationsByClassroom(int classroomId) {
        // 根据课室ID获取所有申请
    }   
}
```

`ApplicationService` 提供了与申请相关的业务逻辑，如检查时间是否冲突，提交申请等。

- **`ApplicationDAO.java`**：与数据库交互的DAO层，负责与数据库的交互，如插入、更新、删除、查询等操作。

```java
public class ApplicationDao {
    public List<Application> getAllApplications() {
        // 获取所有申请
    }

    public Application getApplicationById(int applicationId) {
        // 获取单个申请 by ID
    }

    public void updateApplicationStatus(int applicationId, ApplicationStatus status) {
        // 更新申请状态
    }

    public void updateApplicationStatus(int classroomId, int userId, ApplicationStatus status) {
        // 更新申请状态为已拒绝
    }

    public boolean isTimeAvailable(int classroomId, LocalDateTime startTime, LocalDateTime endTime) {
        // 检查时间是否可用
    }

    public void addApplication(Application application) {
        // 插入申请记录
    }

    public int getActiveApplicationCount(int studentId) {
        // 获取学生当前有效申请数量
    }

    public List<Application> getApplicationsByClassroom(int classroomId) {
        // 获取教室的申请记录
    }

    private Application mapRowToApplication(ResultSet rs) throws SQLException {
        // 将数据库查询结果映射为 Application 对象 (减少重复代码块)
    }
}
```
`ApplicationDao.java`：负责与数据库交互，提供对申请记录的增删改查操作。

### **4. 工具类 (`util` 模块)**

- **`DateUtil.java`**：提供与日期时间相关的工具方法，如判断申请是否过期。

```java
public class DateUtil {

    // 判断预约是否过期
    public static boolean isExpired(LocalDateTime endTime) {
        return LocalDateTime.now().isAfter(endTime);
    }

    // 获取当前时间的字符串表示，格式为：yyyy-MM-dd HH:mm:ss
    public static String getCurrentTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    // 将字符串转换为 LocalDateTime，格式为：yyyy-MM-dd HH:mm:ss
    public static LocalDateTime parseStringToLocalDateTime(String dateTimeStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("时间格式不正确，应该是 yyyy-MM-dd HH:mm:ss", e);
        }
    }

    // 将 LocalDateTime 转换为字符串，格式为：yyyy-MM-dd HH:mm:ss
    public static String formatLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    // 判断当前时间是否在给定的时间区间内
    public static boolean isInRange(LocalDateTime startTime, LocalDateTime endTime) {
        return !LocalDateTime.now().isBefore(startTime) && !LocalDateTime.now().isAfter(endTime);
    }

    // 计算两个 LocalDateTime 之间的时间差（单位：秒）
    public static long getDifferenceInSeconds(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime).getSeconds();
    }

    // 判断当前时间是否在给定的时间之前
    public static boolean isBeforeNow(LocalDateTime dateTime) {
        return LocalDateTime.now().isBefore(dateTime);
    }

    // 判断当前时间是否在给定的时间之后
    public static boolean isAfterNow(LocalDateTime dateTime) {
        return LocalDateTime.now().isAfter(dateTime);
    }

    // 获取并解析开始时间和结束时间
    public static LocalDateTime[] parseStartAndEndTime() {
        // 获取并解析开始时间
        LocalDateTime startTime = InputUtil.getDateTime("请输入开始时间（格式：yyyy-MM-dd HH:mm）：");

        // 获取并解析结束时间
        LocalDateTime endTime = InputUtil.getDateTime("请输入结束时间（格式：yyyy-MM-dd HH:mm）：");

        // 检查结束时间是否早于开始时间
        if (endTime.isBefore(startTime)) {
            System.out.println("❌ 结束时间不能早于开始时间！");
            return null;  // 如果结束时间早于开始时间，返回 null
        }

        // 返回开始时间和结束时间
        return new LocalDateTime[] { startTime, endTime };
    }
}
```

- **`DBUtil.java`**：提供数据库连接池管理的工具类，确保数据库连接的高效与稳定。

```java
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
```
`DBUtil` 提供了与数据库的交互，如获取数据库连接、关闭数据库连接等，是最重要的部分。

- **`InputUtil.java`**：用于处理用户输入，增加了输入的容错机制。

```java
public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    // 获取字符串输入
    public static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // 获取整数输入，增加容错机制
    public static int getInt(String message) {
        int num;
        while (true) {
            try {
                System.out.print(message);
                num = scanner.nextInt();
                scanner.nextLine(); // 清除换行符
                return num;
            } catch (InputMismatchException e) {
                System.out.println("输入错误，请输入有效的整数！");
                scanner.nextLine(); // 清除错误输入
            }
        }
    }

    // 获取正整数
    public static int getPositiveInt(String message) {
        int num;
        while (true) {
            try {
                System.out.print(message);
                num = scanner.nextInt();
                scanner.nextLine(); // 清除换行符
                if (num <= 0) {
                    System.out.println("请输入一个正整数！");
                    continue;
                }
                return num;
            } catch (InputMismatchException e) {
                System.out.println("输入错误，请输入有效的正整数！");
                scanner.nextLine(); // 清除错误输入
            }
        }
    }

    // 获取用户确认输入，限制为 "Y" 或 "N"
    public static boolean getYesNo(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            } else {
                System.out.println("请输入 'Y' 或 'N'！");
            }
        }
    }

    // 获取用户输入的日期时间（格式：yyyy-MM-dd HH:mm）
    public static LocalDateTime getDateTime(String prompt) {
        LocalDateTime dateTime = null;
        while (dateTime == null) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                dateTime = LocalDateTime.parse(input, formatter);  // 解析输入的日期时间
            } catch (Exception e) {
                System.out.println("日期时间格式错误，请使用正确格式（yyyy-MM-dd HH:mm）！");
            }
        }
        return dateTime;
    }
}
```
`InputUtil` 提供了与用户交互的输入工具，如获取字符串、整数、正整数、日期时间等输入，并增加了输入的容错机制。

## 5. MySQL数据库连接过程与表格设计

### **5.1 MySQL数据库连接过程**

在该教室管理系统中，数据库用于存储用户信息、课室信息和申请记录等数据。为了与数据库进行交互，系统使用了 **JDBC (Java Database Connectivity)**，具体流程如下：

#### **5.1.1 数据库连接的配置**

数据库的连接配置主要通过 `DBUtil.java` 类来管理。该类使用了 **HikariCP** 作为数据库连接池，提供更高效的连接管理。`HikariCP` 是一个高性能的 JDBC 连接池，能够有效管理数据库连接，避免频繁的打开和关闭连接操作，使用要导库。

`DBUtil.java` 的数据库连接配置如下：

```java
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
```

**分析**：

- 通过 `HikariConfig` 配置数据库连接池，并且初始化连接池 `HikariDataSource`。这样一来，所有的数据库连接都通过连接池进行管理，可以减少数据库的连接开销。
- `getConnection()` 方法用来从连接池中获取一个数据库连接。
- `close()` 方法用于关闭数据库连接。

#### **5.1.2 数据库操作**

在系统中，所有的数据库操作都通过 DAO 类进行，DAO（Data Access Object）类负责与数据库交互。在 `UserDao.java` 和 `ClassroomDao.java` 还有`ApplicationDao.java`等类中，主要进行数据的查询、插入和更新操作。

举例来说，用户的登录验证操作：

```java
public boolean loginUser(String username, String password) {
    String sql = "SELECT password, role FROM Users WHERE username = ?";
    try (Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String storedPassword = rs.getString("password");
            return verifyPassword(password, storedPassword);  // 校验密码
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // 用户不存在或密码错误
}
```

**分析**：

- `loginUser` 方法通过 SQL 查询语句获取指定用户名的密码，并与输入的密码进行比对。
- 数据库查询操作，还使用了 `PreparedStatement` 来防止 SQL 注入问题。

### **5.2 数据库表格设计**

根据系统需求，以下是数据库的主要表格设计：

#### **5.2.1 用户表 (`Users`)**

用户表存储了系统中的所有用户信息。字段设计如下：

```sql
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','STUDENT') NOT NULL,
  `student_id` varchar(20) DEFAULT NULL,
  `department` varchar(50) DEFAULT NULL,
  `id_card` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

**分析**：

- `id`：用户的唯一标识符。
- `username`：用户名，唯一。
- `password`：密码字段，存储经过加密的密码。
- `role`：用户角色，区分管理员和学生。
- `student_id`、`department`、`id_card`：学生的附加信息，仅学生用户需要填写。如果用户是管理员，这些字段可以为空。
- `id`：主键，自增。
- `username`：唯一键，确保用户名在数据库中唯一。


#### **5.2.2 课室表 (`Classrooms`)**

课室表存储了系统中的所有课室信息。字段设计如下：

```sql
CREATE TABLE `classrooms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `capacity` int NOT NULL,
  `has_multimedia` tinyint(1) DEFAULT '0',
  `is_available` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

**分析**：

- `id`：课室的唯一标识符，主键，自增。
- `name`：课室名称，非空。
- `capacity`：课室的容量，表示可以容纳的学生人数，非空。
- `has_multimedia`：是否配备多媒体设备，默认为不。
- `is_available`：课室是否可用，用于标记课室的当前状态，默认为可用。

#### **5.2.3 申请表 (`Applications`)**

申请表存储了学生提交的课室使用申请信息。字段设计如下：

```sql
CREATE TABLE `applications` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NOT NULL,
  `classroom_id` int NOT NULL,
  `reason` text NOT NULL,
  `status` enum('PENDING','APPROVED','REJECTED','EXPIRED') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'PENDING',
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `expire_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  KEY `classroom_id` (`classroom_id`),
  CONSTRAINT `applications_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `users` (`id`),
  CONSTRAINT `applications_ibfk_2` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

**分析**：
- `id`：申请的唯一标识符，主键，自增。
- `student_id`：申请人的学生ID，外键，引用用户表中的`id`字段。
- `classroom_id`：申请使用的课室ID，外键，引用课室表中的`id`字段。
- `reason`：申请理由，非空。
- `status`：申请状态，包括待审核、已批准、已拒绝、已过期等，默认为待审核。
- `start_time`：申请开始时间，非空。
- `end_time`：申请结束时间，非空。
- `created_time`：申请创建时间，默认为当前时间。
- `expire_time`：申请过期时间，默认为空，在程序里面计算后储存，三天后过期。

### **5.3 定时事件**

系统还包含一个定时事件，用于自动更新申请状态。当申请过期时，自动将其状态更新为“已过期”。
```sql
DELIMITER $$

/*!50106 CREATE DEFINER=`root`@`localhost` EVENT `update_expired_applications` ON SCHEDULE EVERY 1 HOUR STARTS '2025-02-05 21:27:28' ON COMPLETION NOT PRESERVE ENABLE DO UPDATE Applications
    SET status = 'EXPIRED'
    WHERE expire_time < NOW() AND status = 'PENDING' */$$
DELIMITER ;

```
**分析**：
- `update_expired_applications`：定时事件，每小时执行一次。
- `expire_time`：申请过期时间，默认为空，在程序里面计算后储存，三天后过期。
- `status`：申请状态，包括待审核、已批准、已拒绝、已过期等，默认为待审核。
- `NOW()`：当前时间，用于判断申请是否过期。
- `status = 'PENDING'`：只更新状态为“待审核”的申请。

## 6. 用户交互界面设计与逻辑处理

用户交互界面是系统的一个重要部分，特别是在控制台应用程序中，用户与系统的交互主要通过菜单实现。系统根据用户的输入决定跳转到不同的功能模块。

### **6.1 主菜单与角色菜单**

系统提供了不同角色（学生、管理员）对应的菜单。通过输入选择项，用户可以进入相应的功能模块。

例如，`MainMenuController` 类控制主菜单的显示与功能选择：

```java
public class MainMenuController {
    static final UserController userController = new UserController();
    static final ClassroomController classroomController = new ClassroomController();
    static final ApplicationController applicationController = new ApplicationController();

    public static void handleMainMenu() {
        while (true) {
            displayWelcomeMessage();  // 显示欢迎信息

            int choice = InputUtil.getInt("请选择操作：");

            switch (choice) {
                case 1:
                    userController.register();  // 用户注册
                    break;
                case 2:
                    handleLogin();  // 用户登录
                    break;
                case 3:
                    System.out.println("退出系统，感谢您的使用！");
                    System.exit(0); // 退出程序 防止出bug
                    return;
                default:
                    System.out.println("无效选择，请重试！");
            }
        }
    }

    // 显示欢迎信息
    private static void displayWelcomeMessage() {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("\n欢迎使用课室管理系统（当前时间：" + now.format(formatter) + "）");
        System.out.println("\n=== 课室管理系统 ===");
        System.out.println("如果忘记密码，请联系管理员重置！");
        System.out.println("1. 注册");
        System.out.println("2. 登录");
        System.out.println("3. 退出");
    }

    // 处理用户登录后的操作
    private static void handleLogin() {
        MainMenuController.userController.login();

        if (MainMenuController.userController.getCurrentUser() != null) {
            Role userRole = MainMenuController.userController.getCurrentUser().getRole();  // 获取用户角色
            if (userRole == Role.ADMIN) {
                handleAdminMenu();
            } else if (userRole == Role.STUDENT) {
                handleStudentMenu();
            }
        } else {
            System.out.println("登录失败，请检查用户名或密码！");
        }
    }

    // 处理管理员菜单
    private static void handleAdminMenu() {
        System.out.println("欢迎管理员：" + MainMenuController.userController.getCurrentUser().getUsername() + " 登录！");

        boolean isAdminLoggedIn = true;
        while (isAdminLoggedIn) {
            displayAdminMenu();

            int adminChoice = InputUtil.getInt("请选择操作：");
            switch (adminChoice) {
                case 1:
                    MainMenuController.classroomController.viewAllClassrooms();
                    break;
                case 2:
                    MainMenuController.applicationController.viewAllApplications();
                    break;
                case 3:
                    MainMenuController.classroomController.addClassroom();
                    break;
                case 4:
                    MainMenuController.classroomController.deleteClassroom(MainMenuController.classroomController);
                    break;
                case 5:
                    MainMenuController.classroomController.updateClassroomAvailability();
                    break;
                case 6:
                    MainMenuController.applicationController.approveApplication();  // 审批申请
                    break;
                case 7:
                    MainMenuController.classroomController.displayClassroomUsage();  // 调用查看课室使用情况方法
                    break;
                case 8:
                    MainMenuController.classroomController.updateClassroomSchedule();  // 调用修改课室活动安排方法
                    break;
                case 9:
                    System.out.println("退出管理员菜单！");
                    isAdminLoggedIn = false;
                    break;
                default:
                    System.out.println("无效选择，请重新输入！");
            }
        }
    }

    // 处理学生菜单
    private static void handleStudentMenu() {
        System.out.println("欢迎学生：" + MainMenuController.userController.getCurrentUser().getUsername() + " 登录！");

        boolean isStudentLoggedIn = true;
        while (isStudentLoggedIn) {
            displayStudentMenu();

            int studentChoice = InputUtil.getInt("请选择操作：");
            switch (studentChoice) {
                case 1:
                    MainMenuController.classroomController.viewAllClassrooms();
                    break;
                case 2:
                    MainMenuController.applicationController.submitApplication(MainMenuController.userController.getCurrentUser().getId());
                    break;
                case 3:
                    MainMenuController.applicationController.viewAllApplications();
                    break;
                case 4:
                    System.out.println("退出学生菜单！");
                    isStudentLoggedIn = false;
                    break;
                default:
                    System.out.println("无效选择，请重新输入！");
            }
        }
    }

    // 显示管理员菜单
    private static void displayAdminMenu() {
        System.out.println("\n=== 管理员菜单 ===");
        System.out.println("1. 查看所有课室");
        System.out.println("2. 查看所有申请记录");
        System.out.println("3. 添加新课室");
        System.out.println("4. 删除课室");
        System.out.println("5. 修改课室状态");
        System.out.println("6. 审批申请");
        System.out.println("7. 统计课室使用情况");
        System.out.println("8. 修改课室活动安排");
        System.out.println("9. 退出登录");
    }

    // 显示学生菜单
    private static void displayStudentMenu() {
        System.out.println("\n=== 学生菜单 ===");
        System.out.println("1. 查看所有课室");
        System.out.println("2. 提交课室申请");
        System.out.println("3. 查看已提交的申请记录");
        System.out.println("4. 退出登录");
    }
}
```

**分析**：

- `handleMainMenu` 方法是系统的入口菜单，用户通过输入数字选择功能。
- 登录后根据角色（管理员或学生）调用不同的菜单控制逻辑。管理员菜单提供更多的管理功能，学生菜单则聚焦于申请课室和查看申请。
- `displayWelcomeMessage`显示欢迎信息,`displayAdminMenu` 和 `displayStudentMenu` 方法分别用于显示管理员和学生菜单。

### **6.2 功能逻辑处理**

例如，学生提交课室申请的功能通过 `ApplicationController` 类来处理：

```java
public class ApplicationController {
    public void submitApplication(int userId) {
        // 提交申请的逻辑
        int classroomId = InputUtil.getInt("请输入课室ID：");
        String reason = InputUtil.getString("请输入申请理由：");
        LocalDateTime startTime = InputUtil.getDateTime("请输入申请开始时间：");
        LocalDateTime endTime = InputUtil.getDateTime("请输入申请结束时间：");

        if (applicationService.isTimeAvailable(classroomId, startTime, endTime)) {
            LocalDateTime expireTime = LocalDateTime.now().plusDays(3); // 设置有效期
            applicationService.submitApplication(classroomId, userId, reason, startTime, endTime, expireTime);
            System.out.println("申请提交成功，等待审批！");
        } else {
            System.out.println("该时间段的课室不可用！");
        }
    }
}
```

**分析**：

- `submitApplication` 方法引导学生输入申请信息，并调用 `applicationService` 检查课室的时间是否可用。通过 `applicationService.submitApplication` 提交申请并返回反馈。

## 7. logback配置
```xml
<configuration>

    <!-- 设置 HikariCP 日志级别为 DEBUG -->
    <logger name="com.zaxxer.hikari" level="DEBUG"/>

    <!-- 控制台输出 -->
    <appender name="Console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} - %-5level - %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- 将日志输出到文件 -->
    <appender name="File" class="ch.qos.logback.core.FileAppender">
        <file>logs/application.log</file> <!-- 日志文件路径 -->
<!--        &lt;!&ndash; 设置日志文件的滚动策略 &ndash;&gt;-->
<!--        <rollingPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">-->
<!--            &lt;!&ndash; 当日志文件大小超过 10MB 时，进行轮转 &ndash;&gt;-->
<!--            <maxFileSize>10MB</maxFileSize>-->
<!--        </rollingPolicy>-->

<!--        &lt;!&ndash; 设置日志文件的最大历史文件数 &ndash;&gt;-->
<!--        <maxHistory>10</maxHistory>  &lt;!&ndash; 保留最近 10 个日志文件 &ndash;&gt;-->1
        <!-- 设置日志文件的格式 -->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} - %-5level - %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- 设置根日志级别 -->
    <root level="INFO">
        <appender-ref ref="File"/>
    </root>

</configuration>
```

**分析**：

- `logback.xml` 文件用于配置日志记录系统。
- `logger` 元素用于设置特定包或类的日志级别，这里将 `com.zaxxer.hikari` 包的日志级别设置为 `DEBUG`。
-  设置只输出到文件，不输出到控制台。

## 8. 数据库用户数据
- 用于测试程序
```txt
('admin1', 'password123', 'ADMIN', NULL, NULL, NULL),
('admin2', 'password456', 'ADMIN', NULL, NULL, NULL),
('admin3', 'password234', 'ADMIN', NULL, NULL, NULL),
('admin4', 'password123', 'ADMIN', NULL, NULL, NULL),
('admin5', 'password234', 'ADMIN', NULL, NULL, NULL),
('student1', 'password123', 'STUDENT', '3124001234', '计算机科学', '440112'),
('student2', 'password456', 'STUDENT', '3124005678', '自动化', '440113'),
('student3', 'password789', 'STUDENT', '3124009876', '土木工程', '440114'),
('student4', 'password000', 'STUDENT', '3124006543', '生物学', '440115'),
('student5', 'passwordabc', 'STUDENT', '3124004321', '化学工程', '440116'),
('student6', 'password111', 'STUDENT', '3124001357', '计算机科学', '440117'),
('student7', 'password222', 'STUDENT', '3124002468', '电子工程', '440118'),
('student8', 'password333', 'STUDENT', '3124007890', '物理学', '440119'),
('student9', 'password444', 'STUDENT', '3124009081', '化学工程', '440120'),
('student10', 'password555', 'STUDENT', '3124005678', '土木工程', '440121'),
('student11', 'password666', 'STUDENT', '3124002345', '生物学', '440122'),
('student12', 'password777', 'STUDENT', '3124008765', '环境科学', '440123'),
('student13', 'password888', 'STUDENT', '3124002341', '医学', '440124'),
('student14', 'password999', 'STUDENT', '3124002340', '农业科学', '440125'),
('student15', 'password000', 'STUDENT', '3124006542', '语言学', '440126');
```
## 9. pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.ChenDll</groupId>
    <artifactId>classroommanagement</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <dependencies>
        <!-- Logback Classic DB 依赖 -->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.11</version>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-api</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <dependency>
            <groupId>ch.qos.logback.db</groupId>
            <artifactId>logback-core-db</artifactId>
            <version>1.2.11.1</version>
        </dependency>

        <!-- JUnit Jupiter 依赖 -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.11.4</version>
        </dependency>

        <!-- MySQL Connector/J 依赖 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.30</version>
        </dependency>


        <!-- SLF4J API 依赖 -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-nop</artifactId>
            <version>2.0.7</version>
        </dependency>

        <!-- HikariCP 依赖 -->
        <dependency>
            <groupId>com.zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
            <version>6.0.0</version>
        </dependency>

        <dependency>
            <groupId>org.javassist</groupId>
            <artifactId>javassist</artifactId>
            <version>3.30.2-GA</version>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <!-- 编译插件，确保你使用的是正确的 Java 版本 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>

            <!-- JAR 插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.4.2</version>
                <configuration>
                    <archive>
                        <manifestEntries>
                            <Main-Class>com.ChenDll.classroommanagement.Main</Main-Class>
                        </manifestEntries>
                    </archive>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.6.0</version>
                <executions>
                    <execution>
                        <id>shade-when-package</id>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <finalName>${project.artifactId}-${project.version}</finalName>
                            <filters>
                                <filter>
                                    <artifact>*</artifact>
                                </filter>
                            </filters>
                        </configuration>
                    </execution>
                </executions>
            </plugin>


        </plugins>
    </build>
</project>
```
---
