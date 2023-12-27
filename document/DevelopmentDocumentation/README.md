---
typora-root-url: ./imgs
---















# <center>[景点门票管理系统]</center>
<center>第 [ 10 ] 组</center>

|    小组成员    |                  主要工作                  | 总贡献度% |
| :------------: | :----------------------------------------: | :-------: |
| 苏国煜（组长） | 前端软件编写、后端软件架构设计、数据库设计 |    35     |
|      陈进      |           后端软件编写、需求分析           |    25     |
|     周煜婷     |      需求分析、逻辑结构设计、概念设计      |    20     |
|     高瑞艺     |      需求分析、逻辑结构设计、概念设计      |    20     |

<div STYLE="page-break-after: always;"></div>

[TOC]

<div STYLE="page-break-after: always;"></div>

## 背景及意义

### 编写目的

​	景点门票销售管理系统的开发已基本完成。写此项目开发总结报告，为方便我们在以后的项目开发中来更好的实现项目的定制和开发；它不仅是对每次项目开发的总结，更重要的是可以帮助实验人员全面地分析、复盘实验、训练逻辑归纳能力和综合分析能力。在之后的项目开发中，我们会拥有更多有据的资料，提高我们的开发效率。

### 背景

- 开发的软件名称：景点门票销售管理系统

- 本项目开发者：闽江学院数学与数据科学学院（软件学院）“景点门票销售管理系统”开发小组：

  - ​    苏国煜（3222508104 组长）

  - ​    陈 进（3222508104）

  - ​    高瑞艺（3223102115）

  - ​    周煜婷（3222508133）

- 用户单位：闽江学院

### 定义

​	门票景点销售管理系统对于门票销售公司来说，是如何应用信息技术快速提升房屋的销售额的技术平台。对于用户来说，是可以快速了解门票信息以及购票的渠道，为用户提供了便利。

## 需求分析

### 需求概述

- 某景点门票销售管理系统包括对门票信息管理，订单信息管理，客户信息管理，营业员信息管理等


- 在某景点门票销售管理系统中，营业员要为每位客户建立个人信息，客户需要提供自己的姓名，邮箱，年龄。营业员可以对门票的信息进行维护修改，价格，有效时间，库存，门票名等等。以确保门票信息的准确性和时效性。客户可以根据自己的意愿来查询门票的信息，并将购票信息反馈给系统。营业员接收到信息后，将跟进信息反馈给客户，并更新门票信息。实现门票销售、退票管理，统计指定日期的门票销售情况；统计指定月份的门票销售情况；统计指定日期各种价格的门票销售情况；统计指定营业员指定日期的收费情况；


### 功能需求

1. 用户信息管理：售票员不定期对用户信息进行添加、修改、删除处理。
2. 订单查询：用户通过互联网查询已购买的订单信息。
3. 门票信息管理：售票员对门票信息（票价）进行添加、修改、删除处理。
4. 订单管理：售票员对订单信息（销售、退票）进行处理。
5. 退票：用户可对于不满意的体验进行退票。

### 数据流图

#### 顶层图

![顶层图](DFD_Top.png)

#### 0层图

![0层图](DFD_Level_0.png)

### 数据字典

#### 订单所包含数据项的数据字典

| 列名       | 数据类型       | 描述                                              |
| ---------- | -------------- | ------------------------------------------------- |
| OrderID    | int            | 订单唯一标识符 (主键)                             |
| UserID     | int            | 用户ID，与 `user` 表中的 `UserID` 关联 (外键)     |
| TicketID   | int            | 门票ID，与 `ticket` 表中的 `TicketID` 关联 (外键) |
| Quantity   | int            | 订单中的门票数量                                  |
| TotalPrice | decimal(10, 2) | 订单的总价格                                      |
| OrderDate  | timestamp      | 订单创建日期和时间                                |
| SellerId   | int            | 卖家ID，与 `seller` 表中的 `SellerId` 关联 (外键) |


#### 售票员所包含数据项的数据字典

| 列名     | 数据类型     | 描述                  |
| -------- | ------------ | --------------------- |
| SellerId | int          | 卖家唯一标识符 (主键) |
| Name     | varchar(255) | 卖家名称              |


#### 门票所包含数据项的数据字典

| 列名       | 数据类型       | 描述                  |
| ---------- | -------------- | --------------------- |
| TicketID   | int            | 门票唯一标识符 (主键) |
| Price      | decimal(10, 2) | 门票价格              |
| Stock      | int            | 门票库存              |
| TicketName | varchar(255)   | 门票名称              |


#### 用户所包含数据项的数据字典


| 列名     | 数据类型     | 描述                  |
| -------- | ------------ | --------------------- |
| UserID   | int          | 用户唯一标识符 (主键) |
| Username | varchar(50)  | 用户名                |
| Email    | varchar(100) | 用户电子邮件地址      |
| Age      | tinyint      | 用户年龄              |

#### 触发器

##### delete_user_orders触发器


| 属性       | 值                                                |
| ---------- | ------------------------------------------------- |
| 触发器名称 | delete_user_orders                                |
| 触发时机   | BEFORE DELETE                                     |
| 关联表     | user                                              |
| 操作       | 删除 `order` 表中与被删除用户相关的所有订单记录。 |

#### 各种处理操作的数据字典

|   处理名称   | 说明                                   |     流入数据流     |  流出数据流  | 处理                                                       |
| :----------: | :------------------------------------- | :----------------: | :----------: | :--------------------------------------------------------- |
| 用户信息管理 | 售票员对用户信息进行管理               | 查询条件，用户信息 |   用户信息   | 售票员不定期对用户信息进行处理，对用户进行添加、修改、删除 |
|   订单查询   | 用户可通过互联网查询已购买的订单的信息 |      查询条件      | 订单购买信息 | 用户可通过互联网查询订单信息                               |
| 门票信息管理 | 售票员对门票信息进行管理               | 查询条件，门票信息 |   门票信息   | 售票员不定期对门票信息进行处理，对门票进行添加、修改、删除 |
|   订单管理   | 售票员对订单信息进行管理               | 查询条件，用户信息 |   订单信息   | 售票员可以对订单进行删除、退票处理                         |

## 概念设计

由需求分析结果，明确了该系统的功能有订单管理、购票管理、门票管理、退票管理、用户查询管理，5个功能。

### 订单管理

订单管理的功能：营业员不定期对订单信息进行添加、修改、删除。

![订单管理E-R图](E-R_OrderManagement.png)

实体属性定义：

营业员：（营业员编号，姓名）;

门票：（门票编号，门票名，价格，库存）；

用户：（用户编号，用户名，邮箱，年龄）；

### 用户查询

用户查询的功能：用户可通过互联网查询门票信息。

![用户查询E-R图](E-R_UserQuery.png)

实体属性定义：

门票：（门票编号，门票名，价格，库存）；

用户：（用户编号，用户名，邮箱，年龄）；

### 门票管理

门票管理的功能：营业员可对门票信息进行修改。

![门票管理E-R图](E-R_Ticket_Management.png)

实体属性定义：

门票：（门票编号，门票名，价格，有效日期）；

营业员：（营业员编号，姓名）；

### 购票管理

购票管理的功能：用户将购票信息通过门票管理传给营业员，营业员传递给订单中。

![购票管理E-R图](E-R_TicketPurchaseManagement.png)

实体属性定义：

营业员：（营业员编号，姓名）；

门票：（门票编号，门票名，价格，有效日期）；

用户：（用户编号，用户名，邮箱，年龄）；

订单：（订单编号，用户编号，营业员编号，门票编号，购买数量，总价格，购买日期）；

### 退票管理

退票管理的功能是：营业员管理用户的退票请求。

![退票管理E-R图](E-R_RefundManagement.png)



实体属性定义：

营业员：（营业员编号，姓名）；

用户：（用户编号，用户名，邮箱，年龄）；

订单：（订单编号，用户编号，营业员编号，门票编号，购买数量，总价格，购买时间）；

### 总E-R图

实体属性定义：

营业员：（营业员编号，姓名）;

门票：（门票编号，门票名，价格，库存）；

用户：（用户编号，用户名，邮箱，年龄）；

订单：（订单编号，用户编号，营业员编号，门票编号，购买数量，总价格，购买时间）；

![总E-R图](E-R_Overall.png)

## 逻辑结构设计

### 与 E-R 图对应的关系模式
#### 实体所对应的关系模式：
1. 订单（<u>订单ID</u>，<u>用户ID</u>，<u>门票ID</u>，数量，总价，购买时间，售票员ID，售票员名，订单状态，用户名，Email，年龄，价格，库存，门票名）
2. 用户（<u>用户ID</u>，用户名，Email，年龄）
3. 门票（<u>门票ID</u>，价格，库存，门票名）
4. 售票员（<u>售票员ID</u>，售票员名）

#### 联系所对应的关系模式：
1)  用户信息管理（<u>用户ID，售票员ID</u>）
2)  订单查询（<u>用户ID，订单ID</u>）
3)  购买（<u>用户ID，订单ID</u>）
4)  订单管理（<u>用户ID，订单ID，售票员ID</u>）
## 优化后的数据模型
1. 订单（<u>订单ID</u>，用户ID，<u>门票ID</u>，数量，总价，订单日期，售票员ID，订单状态）3NF
2. 用户（<u>用户ID</u>，用户名称，Email，年龄）3NF
3. 用户信息管理（<u>用户ID</u>，<u>售票员ID</u>）3NF
4. 订单查询（<u>用户ID</u>，订单ID）3NF
5. 购买（<u>用户ID</u>，<u>门票ID</u>）（用户ID和门票ID是外键）3NF
6. 订单管理（<u>用户ID</u>，<u>订单ID</u>，<u>售票员ID</u>）（用户ID、订单ID和售票员ID是外键）3NF
7. 门票（<u>门票ID</u>，价格，库存，门票名）3NF
8. 售票员（<u>售票员ID</u>，售票员姓名）3NF

## 数据库实施

### seller表 - 售票员表

```mysql
DROP TABLE IF EXISTS `seller`;
CREATE TABLE `seller` (
  `SellerId` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`SellerId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
```

### ticket表 - 门票表

```mysql
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket` (
  `TicketID` int NOT NULL AUTO_INCREMENT,
  `Price` decimal(10, 2) NOT NULL,
  `Stock` int NOT NULL,
  `TicketName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`TicketID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
```

### user表 - 用户表

```mysql
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `UserID` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Age` tinyint NOT NULL,
  PRIMARY KEY (`UserID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
```

### order表 - 订单表

```mysql
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `OrderID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `TicketID` int NULL DEFAULT NULL,
  `Quantity` int NOT NULL,
  `TotalPrice` decimal(10, 2) NOT NULL,
  `OrderDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `SellerId` int NOT NULL,
  `State` int NOT NULL,
  PRIMARY KEY (`OrderID`) USING BTREE,
  INDEX `UserID`(`UserID` ASC) USING BTREE,
  INDEX `TicketID`(`TicketID` ASC) USING BTREE,
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
```

### 触发器

```mysql
DROP TRIGGER IF EXISTS `delete_user_orders`;
delimiter;
;
CREATE TRIGGER `delete_user_orders` BEFORE DELETE ON `user` FOR EACH ROW BEGIN
DELETE FROM `order`
WHERE UserID = OLD.UserID;
END;
;
delimiter;
```

## 数据关系图

![数据关系图](DataRelationshipDiagram.png)

## 程序设计语言

### 选定环境

- MySQL 8.0

- Navicat Premium 16

- IntelliJ IDEA 2023.2.2

- Android Studio 2023.1.1

- Springboot 3.2.0

- Flutter 3.10.5

- Java 17

- Dart 3.0.5

- Apache Maven 3.9.3

- Ubuntu 22.04 LTS

- Windows 11 x64

### 后端程序连接数据库通用代码

```java
// 建立数据库连接
    String url = "jdbc:mysql://localhost:3306/attraction_ticket_sales?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    String username = "root";
    String password = "password";

    try {
        this.connection = DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
        e.printStackTrace();
    }
```

### 用户信息管理主要代码

```java
/**
 * 从数据库中获取所有用户信息
 * @return
 */
@Override
public List<User> getUsers() {
    List<User> users = new ArrayList<>();
    String sql = "SELECT * FROM User";
    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("UserID"));
            user.setName(resultSet.getString("Username"));
            user.setEmail(resultSet.getString("Email"));
            users.add(user);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return users;
}

/**
 * 添加用户
 * @param user
 */
@Override
public void addUser(User user) {
    // 实现添加用户
    String sql = "INSERT INTO User(Username, Email) VALUES (?, ?)";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getEmail());
        int rows = preparedStatement.executeUpdate();
        if (rows > 0) {
            System.out.println("添加成功！");
        } else {
            System.out.println("添加失败！");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

/**
 * 修改用户信息
 * @param user
 */
@Override
public void updateUser(User user) {
    // 从user中获取id和其它属性实现修改
    String sql = "UPDATE User SET Username = ?, Email = ? WHERE UserID = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setLong(3, user.getId());
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

/**
 * 删除用户信息
 * @param id
 */
@Override
public void deleteUser(Long id) {
    // 实现删除用户
    String sql = "DELETE FROM User WHERE UserID = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setLong(1, id);
        int rows = preparedStatement.executeUpdate();

        if (rows > 0) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败！");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

/**
 * 根据用户属性信息获取用户信息
 * @param name
 * @param email
 * @return
 */
@Override
public List<User> searchUsers(String name, String email) {
    // 条件查询用户
    List<User> users = new ArrayList<>();
    String sql = "SELECT * FROM User WHERE Username LIKE ? AND Email LIKE ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, "%" + name + "%");
        preparedStatement.setString(2, "%" + email + "%");
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("UserID"));
                user.setName(resultSet.getString("Username"));
                user.setEmail(resultSet.getString("Email"));
                users.add(user);
            }
        }

        System.out.println(preparedStatement.toString());
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return users;
}

/**
 * 根据用户id获取用户名
 * @param id
 * @return
 */
@Override
public String getUserNameById(Long id) {
    String Username = null;
    String sql = "SELECT Username FROM user where UserID="+id;
    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
        resultSet.next();
        Username= resultSet.getString("Username");
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return Username;
}
```

### 门票信息管理主要代码

```java
/**
 * 从数据库中获取所有门票信息
 *
 * @return
 */
@Override
public List<Ticket> getTickets() {
    List<Ticket> Tickets = new ArrayList<>();
    String sql = "SELECT * FROM ticket";
    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
        while (resultSet.next()) {
            Ticket ticket = new Ticket();
            ticket.setTicketID(resultSet.getLong("TicketID"));
            ticket.setTicketName(resultSet.getString("TicketName"));
            ticket.setStock(resultSet.getInt("Stock"));
            ticket.setPrice(resultSet.getDouble("Price"));
            Tickets.add(ticket);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return Tickets;
}

/**
 * 添加门票
 *
 * @param ticket
 */
@Override
public void addTicket(Ticket ticket) {
    System.out.println("添加了门票！" + ticket);

    String sql = "insert into ticket(TicketID, Price, Stock, TicketName) VALUES (?,?,?,?)";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setObject(1, ticket.getTicketID());
        preparedStatement.setObject(2, ticket.getPrice());
        preparedStatement.setObject(3, ticket.getStock());
        preparedStatement.setObject(4, ticket.getTicketName());
        int rows = preparedStatement.executeUpdate();
        if (rows > 0) {
            System.out.println("添加成功！");
        } else {
            System.out.println("添加失败！");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

/**
 * 更新门票
 *
 * @param ticket
 */
@Override
public void updateTicket(Ticket ticket) {
    String sql = "update ticket set Price=?,Stock=?,TicketName=? where TicketID=?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setObject(1, ticket.getPrice());
        preparedStatement.setObject(2, ticket.getStock());
        preparedStatement.setObject(3, ticket.getTicketName());
        preparedStatement.setObject(4, ticket.getTicketID());
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

/**
 * 删除门票
 *
 * @param id
 */
@Override
public void deleteTicket(Long id) {
    System.out.println("删除了门票！" + id);

    String sql = "delete from ticket where TicketID =?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setLong(1, id);
        int rows = preparedStatement.executeUpdate();

        if (rows > 0) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败！");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

/**
 * 搜索门票
 *
 * @param ticketName
 * @param price
 * @param stock
 * @return
 */
@Override
public List<Ticket> searchTickets(String ticketName, Double price, Integer stock) {
    List<Ticket> tickets = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT * FROM ticket WHERE 1=1");
    List<Object> params = new ArrayList<>();

    if (ticketName != null && !ticketName.isEmpty()) {
        sql.append(" AND ticketName LIKE ?");
        params.add("%" + ticketName + "%");
    }

    if (price != null) {
        sql.append(" AND price = ?");
        params.add(price);
    }

    if (stock != null) {
        sql.append(" AND stock = ?");
        params.add(stock);
    }

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
        for (int i = 0; i < params.size(); i++) {
            preparedStatement.setObject(i + 1, params.get(i));
        }

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setPrice(resultSet.getDouble("Price"));
                ticket.setStock(resultSet.getInt("Stock"));
                ticket.setTicketName(resultSet.getString("TicketName"));
                ticket.setTicketID(resultSet.getLong("TicketID"));
                tickets.add(ticket);
            }
        }

        System.out.println(preparedStatement.toString());
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return tickets;
}

/**
 * 根据门票id获取门票名
 * @param id
 * @return
 */
@Override
public String getTicketNameById(Long id) {
    String TicketName = null;
    String sql = "SELECT TicketName FROM ticket where TicketID="+id;
    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
        resultSet.next();
        TicketName= resultSet.getString("TicketName");
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return TicketName;
}
```

### 订单信息管理主要代码

```java
/**
 * 从数据库中获取所有订单信息
 *
 * @return
 */
@Override
public List<OrderVO> getOrders() {
    System.out.println("获取了所有订单信息");

    List<OrderVO> orders = new ArrayList<>();
    String sql = "SELECT * FROM `order`";
    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
        while (resultSet.next()) {
            OrderVO order = OrderVO.builder()
                    .orderID(resultSet.getLong("OrderID"))
                    .userName(userDao.getUserNameById(resultSet.getLong("UserID")))
                    .ticketName(ticketDao.getTicketNameById(resultSet.getLong("TicketID")))
                    .quantity(resultSet.getInt("Quantity"))
                    .totalPrice(resultSet.getDouble("TotalPrice"))
                    .orderDate(new Date(resultSet.getTimestamp("OrderDate").getTime()))
                    .sellerName(sellerDao.getSellerNameById(resultSet.getLong("SellerId")))
                    .state(resultSet.getInt("State"))
                    .build();
            orders.add(order);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return orders;
}

/**
 * 退单
 *
 * @param id
 */
@Override
public void refundOrder(Long id) {
    System.out.println("进行了退单");

    String sql = "UPDATE `order` SET State=0 WHERE OrderID = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    sql = "Select TicketID from `order` where OrderID = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setLong(1, id);
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();
        resultSet.next();
        sql = "UPDATE ticket SET Stock=Stock+1 WHERE TicketID = ?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
        preparedStatement1.setObject(1, resultSet.getObject(1));
        preparedStatement1.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

/**
 * 搜索订单
 *
 * @param orderID
 * @param userID
 * @param orderDate
 * @param sellerId
 * @param state
 * @return
 */
@Override
public List<OrderVO> searchOrders(Integer orderID, String userName, String ticketName, Date orderDate, String sellerName, Integer state) {
    List<OrderVO> orders = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT * FROM `order` WHERE 1=1");
    List<Object> params = new ArrayList<>();

    if (userName != null && !userName.isEmpty()) {
        sql.append(" AND UserID IN (SELECT UserID FROM `user` WHERE Username LIKE ?)");
        params.add("%" + userName + "%");
    }
    if (sellerName != null && !sellerName.isEmpty()) {
        sql.append(" AND SellerId IN (SELECT SellerId FROM seller WHERE Name LIKE ?)");
        params.add("%" + sellerName + "%");
    }
    if (ticketName != null && !ticketName.isEmpty()) {
        sql.append(" AND TicketID IN (SELECT TicketID FROM ticket WHERE Ticketname LIKE ?)");
        params.add("%" + ticketName + "%");
    }

    if (orderDate != null) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(orderDate);
        sql.append(" AND OrderDate = ?");
        params.add(formattedDate);
    }

    if (orderID != null) {
        sql.append(" AND OrderID = ?");
        params.add(orderID);
    }

    if (state != null) {
        sql.append(" AND State = ?");
        params.add(state);
    }

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
        for (int i = 0; i < params.size(); i++) {
            preparedStatement.setObject(i + 1, params.get(i));
        }

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                OrderVO order = OrderVO.builder()
                        .orderID(resultSet.getLong("OrderID"))
                        .userName(userDao.getUserNameById(resultSet.getLong("UserID")))
                        .ticketName(ticketDao.getTicketNameById(resultSet.getLong("TicketID")))
                        .quantity(resultSet.getInt("Quantity"))
                        .totalPrice(resultSet.getDouble("TotalPrice"))
                        .orderDate(new Date(resultSet.getTimestamp("OrderDate").getTime()))
                        .sellerName(sellerDao.getSellerNameById(resultSet.getLong("SellerId")))
                        .state(resultSet.getInt("State"))
                        .build();
                orders.add(order);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return orders;
}
```

### 数据报表主要代码

```java
/**
 * 获取门票销售报告
 *
 * @return
 */
@Override
public List<ReportVO> getReports() {
    List<ReportVO> reportVOS = new ArrayList<>();
    String query = "SELECT OrderDate, TotalPrice, SUM(Quantity) as Quantity FROM `order` WHERE state = 1 GROUP BY OrderDate, TotalPrice";

    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {

        Date currentDate = null;
        ReportVO reportVO = null;
        while (resultSet.next()) {
            Date date = resultSet.getDate("OrderDate");
            if (!date.equals(currentDate)) {
                if (reportVO != null) {
                    reportVOS.add(reportVO);
                }
                currentDate = date;
                reportVO = new ReportVO();
                reportVO.setDate(currentDate);
                reportVO.setSalesByPrice(new TreeMap<>());  // 使用TreeMap替换HashMap
            }
            Double price = resultSet.getDouble("TotalPrice");
            int quantity = resultSet.getInt("Quantity");
            reportVO.getSalesByPrice().put(price, quantity);
            reportVO.setTotalSales(reportVO.getTotalSales() + quantity);
        }
        if (reportVO != null) {
            reportVOS.add(reportVO);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return reportVOS;
}

/**
 * 获取售票员销售报告
 *
 * @return
 */
@Override
public List<ClerkReportVO> getClerkReports() {
    Map<String, ClerkReportVO> clerkReportMap = new HashMap<>();
    String query = "SELECT SellerId, OrderDate, SUM(TotalPrice) as TotalCharge FROM `order` WHERE State = 1 GROUP BY SellerId, OrderDate";

    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {

        while (resultSet.next()) {
            String clerkName = sellerDao.getSellerNameById(resultSet.getLong("SellerId"));
            Date date = resultSet.getDate("OrderDate");
            Double totalCharge = resultSet.getDouble("TotalCharge");

            String key = clerkName + date.toString();
            if (clerkReportMap.containsKey(key)) {
                ClerkReportVO existingReport = clerkReportMap.get(key);
                existingReport.setCharge(existingReport.getCharge() + totalCharge);
            } else {
                ClerkReportVO newReport = ClerkReportVO.builder()
                        .clerkName(clerkName)
                        .date(date)
                        .charge(totalCharge)
                        .build();
                clerkReportMap.put(key, newReport);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return new ArrayList<>(clerkReportMap.values());
}

/**
 * 从结果集中获取数据并创建ClerkReport对象
 *
 * @param resultSet
 * @return
 * @throws SQLException
 */
private ClerkReportVO createClerkReportFromResultSet(ResultSet resultSet) throws SQLException {
    String clerkName = sellerDao.getSellerNameById(resultSet.getLong("SellerId"));
    Date date = resultSet.getDate("OrderDate");
    Double totalCharge = resultSet.getDouble("TotalCharge");  // 获取售票员当天的总销售额

    return ClerkReportVO.builder()
            .clerkName(clerkName)
            .date(date)
            .charge(totalCharge)  // 设置售票员当天的总销售额
            .build();
}
```

## 数据库维护

​	当试运行数据库合格后，数据库开发设计的工作就基本完成了，接下来就是正式运行中的调试，因为该系统比较简单，数据量小，数据库中几乎不会发生什么大的变化，但是还是需要做好数据的备份。

- 在 MySQL 中我们可以利用备份数据库的功能对已经设计好的数据做手动备份

```bash
mysqldump -u root -p test >backup_file.sql
```

- 也可以利用Ubuntu Linux 的cron作业设置定时维护计划：

```shell
0 1 * * * /usr/bin/mysqldump -u root -p'password' test >/tmp/mysql_backup/test_backup_file_$(date +\%Y\%m\%d).sql #设置一个在每天上午1点执行的定时备份cron作业任务
```

​	如果数据库受到破坏或系统故障，我们便可轻松的利用备份文件恢复数据库的数据。 当然也可以利用其他各种方法进行数据维护。

## 系统运行情况及用户界面

![用户管理页面_0](UserManagement_0.png)

![用户管理页面_1](UserManagement_1.png)

![用户管理页面_2](UserManagement_2.png)

![门票管理页面_0](Ticket_Management_0.png)

![门票管理页面_1](Ticket_Management_1.png)

![门票管理系统页面_2](Ticket_Management_2.png)

![门票管理页面_3](Ticket_Management_3.png)

![订单管理页面_0](Order_Management_0.png)

![订单管理页面_1](Order_Management_1.png)

![数据报表页面_0](Report_0.png)

![数据报表页面_1](Report_1.png)
