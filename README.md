# 基于Java + Dart的简单门票管理系统设计与实现

### 功能介绍

平台采用B/S结构，后端采用主流的Springboot框架进行开发，前端采用Flutter框架进行开发。

整个平台**只有**后台部分。

- 后台功能包括：用户管理、门票管理、订单管理和销售数据概览

### 适合人群

大学生、课程作业


### 代码结构

- document目录是设计项目、数据库所用到的文档
- source目录是前端和后端软件的代码
  - TicketingManagementSystem_backend目录是后端代码
  - TicketingManagementSystem_frontend目录是前端代码

### 部署运行

首先，clone本仓库到本地

```bash
git clone https://github.com/CN-Scars/TicketingManagementSystem.git
```

#### 后端运行步骤

1. 下载JDK 17，并配置环境变量

2. Clone本代码后，使用IntelliJ IDEA打开TicketingManagementSystem_backend目录

3. 打开TicketingManagementSystem_backend/src源码目录中org.scars.server.dao.Impl包下每个文件，配置每个Impl实现类构造函数中的数据库连接相关信息

4. 安装MySQL 8.0数据库，并创建数据库，建库SQL如下：
```mysql
CREATE DATABASE IF NOT EXISTS attraction_ticket_sales DEFAULT CHARSET utf8 COLLATE utf8_general_ci
```
5. 启动后端服务：点击IDEA顶部run按钮


#### 前端运行步骤

1. 安装Dart 3.0.5、Flutter 3.10.5

3. 进入TicketingManagementSystem_frontend目录下，安装依赖，执行:
```bash
flutter pub get
```
4. 运行项目
```bash
flutter run
```
5. 在自动弹出的浏览器窗口运行项目


### 界面预览

![用户管理页面_0](.\document\DevelopmentDocumentation\imgs\UserManagement_0.png)

![用户管理页面_1](.\document\DevelopmentDocumentation\imgs\UserManagement_1.png)

![用户管理页面_2](.\document\DevelopmentDocumentation\imgs\UserManagement_2.png)

![门票管理页面_0](.\document\DevelopmentDocumentation\imgs\Ticket_Management_0.png)

![门票管理页面_1](.\document\DevelopmentDocumentation\imgs\Ticket_Management_1.png)

![门票管理系统页面_2](.\document\DevelopmentDocumentation\imgs\Ticket_Management_2.png)

![门票管理页面_3](.\document\DevelopmentDocumentation\imgs\Ticket_Management_3.png)

![订单管理页面_0](.\document\DevelopmentDocumentation\imgs\Order_Management_0.png)

![订单管理页面_1](.\document\DevelopmentDocumentation\imgs\Order_Management_1.png)

![数据报表页面_0](.\document\DevelopmentDocumentation\imgs\Report_0.png)

![数据报表页面_1](.\document\DevelopmentDocumentation\imgs\Report_1.png)
