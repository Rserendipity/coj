## 概述

### 项目名称

COJ（Code Online Judge）

### 项目简介

基于 **SSM + Vue3** 的 在线编程题目评测系统（Online Judge），支持 **Java** / **C/C++** 语言（已为更多语言支持预留接口）。

### 业务流程：

1. 管理员 预设题目以及测试用例
2. 用户 注册并登录
3. 用户 查看题目、编写代码、提交代码
4. 系统 编译并执行代码，用户代码执行结果
5. 系统 比对用户输出和预设测试用例，给用户返回结果

## 运行此项目

### 1. 环境参考

#### 系统环境

- 测试：Windows 11
- 部署：Ubuntu 20.04.1 LTS

#### 开发工具版本

前端：

- NodeJS：16.13.0
- NPM：8.1.0
- Vite：5.0.12

后端：

- Java：8
- SpringBoot：2.6.13
- Mysql：8.0.21
- Nginx：1.18.0

### 2. 运行步骤

#### 1）下载源码

```sh
git clone https://github.com/Rserendipity/coj.git
```

使用 IDEA 打开`coj`项目

#### 2）导入数据库

使用 IDEA 执行下面文件中的 sql 脚本d
`coj-backend/src/main/resources/sql/coj.sql`

或者 执行以下命令导入数据

```sh
mysql -u root -p < coj.sql
```

!!! danger Tip
此操作会删掉已有的名为 COJ 的数据库，如有需要请先备份数据
!!!

### 3）启动后端

使用 IDEA 刷新 Maven 依赖

修改配置文件：`coj-backend/src/main/resources/application.yml`的数据库配置（7、8行）

```yml
...
spring:
  datasource:
    username: "xxx" # 替换为你的数据库用户名
    password: "xxx" # 替换为你的数据库密码
...
```

运行`coj-backend/src/main/java/com/cjj/coj/CojBackendApplication`类

### 4）启动前端

在`coj-front`文件夹中打开命令行并执行：

```sh
npm install
```

```sh
npm run dev
```

### 5）访问项目

根据前端启动后的提示，访问网址

例如：`http://localhost:8080`（WebPack）或`http://localhost:5173`（Vite）

## 更多

如果喜欢的话，给我一个 star 吧，谢谢！

[项目地址](https://github.com/Rserendipity/coj)
