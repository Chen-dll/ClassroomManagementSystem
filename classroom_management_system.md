---

# 教室管理系统项目说明与代码分析

## 项目概述

该教室管理系统旨在为学校或教育机构提供一个易于使用的平台，帮助管理员管理课室的使用情况，同时为学生提供便捷的课室申请功能。系统使用基于角色的访问控制，区分管理员和学生的操作权限，确保不同角色的用户能够执行相应的操作。系统的主要功能包括用户管理、课室管理、申请管理以及菜单系统。

## 项目架构设计

为确保系统的可维护性与可扩展性，采用了**分层架构**设计。系统主要包括以下几个模块：

- **`user` 模块**：负责用户的注册、登录和权限控制。
- **`classroom` 模块**：管理课室信息，包括添加、删除和修改课室。
- **`application` 模块**：处理学生的课室申请，管理员的审批功能。
- **`util` 模块**：提供通用工具类，如日期时间处理和数据库连接管理。

每个模块有明确的职责，确保了代码的清晰与模块化管理。

## 代码实现

### **1. 用户管理模块 (`user` 模块)**

- **`User.java`**：定义了系统中的用户实体类，包含用户名、密码、角色等信息，角色使用了枚举类 `Role` 来区分管理员和学生。

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

    //
```java
    // 构造函数与 Getter/Setter省略
}
```

课室实体类提供了课室的基本信息，以供后续的课室管理操作使用。

- **`ClassroomController.java`**：实现课室管理的控制逻辑，支持查看、添加和修改课室信息。

```java
public class ClassroomController {
    private final ClassroomService classroomService = new ClassroomService();

    public void viewAllClassrooms() {
        // 查看所有课室信息
    }

    public void addClassroom() {
        // 添加课室信息
    }

    public void updateClassroomAvailability() {
        // 更新课室状态
    }
}
```

`ClassroomController` 负责处理来自用户的请求，调用 `ClassroomService` 进行具体的业务操作。

- **`ClassroomService.java`**：包含课室管理的业务逻辑，执行添加、删除、修改课室的操作。

```java
public class ClassroomService {
    public boolean updateClassroomAvailability(int id, boolean isAvailable) {
        // 更新课室的可用状态
    }

    public boolean addClassroom(String name, int capacity, boolean hasMultimedia, boolean isAvailable) {
        // 添加新的课室
    }
}
```

`ClassroomService` 处理了业务逻辑层的课室相关操作，例如添加课室、修改课室可用状态等。

### **3. 申请管理模块 (`application` 模块)**

- **`Application.java`**：定义课室申请的实体类，保存申请ID、用户ID、课室ID、申请状态、开始时间等信息。

```java
public class Application {
    private int id;
    private int classroomId;
    private int userId;
    private String reason;
    private ApplicationStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // 构造函数与 Getter/Setter省略
}
```

`Application` 类封装了申请的基本数据结构，为后续的申请提交与审批提供支持。

- **`ApplicationController.java`**：处理学生提交申请与管理员审批的逻辑。

```java
public class ApplicationController {
    public void submitApplication(int userId) {
        // 学生提交申请的逻辑
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

`ApplicationController` 类接收用户输入，提交课室申请，或由管理员进行审批。

- **`ApplicationService.java`**：负责申请管理的核心业务逻辑，如检查时间是否冲突、申请状态更新等。

```java
public class ApplicationService {
    public void submitApplication(int classroomId, int userId, String reason, LocalDateTime startTime, LocalDateTime endTime) {
        // 提交申请的业务逻辑
    }

    public boolean isTimeAvailable(int classroomId, LocalDateTime startTime, LocalDateTime endTime) {
        // 检查时间是否冲突
    }
}
```

`ApplicationService` 提供了与申请相关的业务逻辑，如检查时间是否冲突，提交申请等。

### **4. 工具类 (`util` 模块)**

- **`DateUtil.java`**：提供与日期时间相关的工具方法，如判断申请是否过期。

```java
public class DateUtil {
    public static boolean isExpired(LocalDateTime endTime) {
        return LocalDateTime.now().isAfter(endTime);
    }
}
```

- **`DBUtil.java`**：提供数据库连接池管理的工具类，确保数据库连接的高效与稳定。

```java
public class DBUtil {
    public static Connection getConnection() throws SQLException {
        // 获取数据库连接
    }
}
```

- **`InputUtil.java`**：用于处理用户输入，增加了输入的容错机制。

```java
public class InputUtil {
    public static int getInt(String prompt) {
        // 获取整数输入，增加错误处理
    }
}
```

## 5. MySQL数据库连接过程与表格设计

### **5.1 MySQL数据库连接过程**

在该教室管理系统中，数据库用于存储用户信息、课室信息和申请记录等数据。为了与数据库进行交互，系统使用了 **JDBC (Java Database Connectivity)**，具体流程如下：

#### **5.1.1 数据库连接的配置**

数据库的连接配置主要通过 `DBUtil.java` 类来管理。该类使用了 **HikariCP** 作为数据库连接池，提供更高效的连接管理。`HikariCP` 是一个高性能的 JDBC 连接池，能够有效管理数据库连接，避免频繁的打开和关闭连接操作。

`DBUtil.java` 的数据库连接配置如下：

```java
public class DBUtil {
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/classroom_management");
        config.setUsername("root");
        config.setPassword("your_password");
        config.setMaximumPoolSize(10);  // 设置最大连接池大小
        config.setMinimumIdle(5);       // 设置最小空闲连接
        config.setIdleTimeout(30000);   // 设置空闲连接的超时时间
        config.setConnectionTimeout(30000);  // 设置连接超时时间
        config.setConnectionTestQuery("SELECT 1");  // 测试连接的查询

        // 初始化连接池
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

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

在系统中，所有的数据库操作都通过 DAO 类进行，DAO（Data Access Object）类负责与数据库交互。在 `UserDao.java` 和 `ClassroomDao.java` 等类中，主要进行数据的查询、插入和更新操作。

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
- 该操作是典型的数据库查询操作，使用了 `PreparedStatement` 来防止 SQL 注入问题。

### **5.2 数据库表格设计**

根据系统需求，以下是数据库的主要表格设计：

#### **5.2.1 用户表 (`Users`)**

用户表存储了系统中的所有用户信息。字段设计如下：

```sql
CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,  -- 角色: ADMIN 或 STUDENT
    student_id VARCHAR(20),
    department VARCHAR(50),
    id_card VARCHAR(20),
    failed_attempts INT DEFAULT 0,
    lock_until DATETIME
);
```

**分析**：

- `id`：用户的唯一标识符。
- `username`：用户名，唯一。
- `password`：密码字段，存储经过加密的密码。
- `role`：用户角色，区分管理员和学生。
- `student_id`、`department`、`id_card`：学生的附加信息，仅学生用户需要填写。
- `failed_attempts`：记录登录失败的次数，用于防止暴力破解。
- `lock_until`：记录用户锁定的时间，如果失败次数超过限制，用户将被暂时锁定。

#### **5.2.2 课室表 (`Classrooms`)**

课室表存储了系统中的所有课室信息。字段设计如下：

```sql
CREATE TABLE Classrooms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    has_multimedia BOOLEAN DEFAULT FALSE,
    is_available BOOLEAN DEFAULT TRUE
);
```

**分析**：

- `id`：课室的唯一标识符。
- `name`：课室名称。
- `capacity`：课室的容量，表示可以容纳的学生人数。
- `has_multimedia`：是否配备多媒体设备。
- `is_available`：课室是否可用，用于标记课室的当前状态。

#### **5.2.3 申请表 (`Applications`)**

申请表存储了学生提交的课室使用申请信息。字段设计如下：

```sql
CREATE TABLE Applications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    classroom_id INT NOT NULL,
    reason VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,  -- 申请状态: PENDING, APPROVED, REJECTED, EXPIRED
    start_time DATETIME,
    end_time DATETIME,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    expire_time DATETIME,
    FOREIGN KEY (student_id) REFERENCES Users(id),
    FOREIGN KEY (classroom_id) REFERENCES Classrooms(id)
);
```

**分析**：

- `id`：申请的唯一标识符。
- `student_id`：申请的学生ID，外键关联到 `Users` 表。
-

 `classroom_id`：申请的课室ID，外键关联到 `Classrooms` 表。

- `reason`：申请理由。
- `status`：申请的状态（待审批、已批准、已拒绝、已过期）。
- `start_time`、`end_time`：申请的时间段。
- `created_time`：申请的创建时间，默认当前时间。
- `expire_time`：申请的过期时间，通常为三天后。

## 6. 用户交互界面设计与逻辑处理

用户交互界面是系统的一个重要部分，特别是在控制台应用程序中，用户与系统的交互主要通过菜单实现。系统根据用户的输入决定跳转到不同的功能模块。

### **6.1 主菜单与角色菜单**

系统提供了不同角色（学生、管理员）对应的菜单。通过输入选择项，用户可以进入相应的功能模块。

例如，`MainMenuController` 类控制主菜单的显示与功能选择：

```java
public class MainMenuController {
    public static void handleMainMenu() {
        while (true) {
            displayWelcomeMessage();
            int choice = InputUtil.getInt("请选择操作：");

            switch (choice) {
                case 1:
                    userController.register();  // 用户注册
                    break;
                case 2:
                    handleLogin();  // 用户登录
                    break;
                case 3:
                    System.exit(0); // 退出程序
                    return;
                default:
                    System.out.println("无效选择，请重试！");
            }
        }
    }

    private static void handleLogin() {
        userController.login();
        if (userController.getCurrentUser() != null) {
            Role userRole = userController.getCurrentUser().getRole();  // 获取用户角色
            if (userRole == Role.ADMIN) {
                handleAdminMenu();
            } else if (userRole == Role.STUDENT) {
                handleStudentMenu();
            }
        } else {
            System.out.println("登录失败，请检查用户名或密码！");
        }
    }
}
```

**分析**：

- `handleMainMenu` 方法是系统的入口菜单，用户通过输入数字选择功能。
- 登录后根据角色（管理员或学生）调用不同的菜单控制逻辑。管理员菜单提供更多的管理功能，学生菜单则聚焦于申请课室和查看申请。

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

---
