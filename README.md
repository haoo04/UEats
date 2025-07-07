# 苍穹外卖管理系统

## 📖 项目简介

苍穹外卖管理系统是一个基于 Spring Boot 开发的现代化外卖平台解决方案，为餐厅提供全方位的数字化管理服务。系统采用前后端分离架构，支持管理端和用户端双端应用，实现了从菜品管理到订单处理的完整业务流程。

## ✨ 功能特性

### 管理端功能
- **👥 员工管理**：员工信息维护、权限管理、登录认证
- **📂 分类管理**：菜品分类、套餐分类的增删改查
- **🍜 菜品管理**：菜品信息管理、口味配置、图片上传
- **🍱 套餐管理**：套餐配置、菜品关联、价格设置
- **📋 订单管理**：订单查询、状态更新、订单统计
- **📊 数据统计**：营业数据统计、销售报表、用户分析
- **🔧 系统设置**：基础配置、参数管理

### 用户端功能
- **🔐 用户认证**：微信登录、用户注册
- **🛒 购物车**：商品添加、数量调整、批量操作
- **📍 地址管理**：收货地址维护、默认地址设置
- **💳 订单支付**：微信支付、订单跟踪
- **📱 个人中心**：订单历史、个人信息管理

## 🛠 技术栈

### 后端技术
- **框架**：Spring Boot 2.7.3
- **数据库**：MySQL 8.0+
- **ORM**：MyBatis + MyBatis-Plus
- **连接池**：Druid
- **认证**：JWT (JSON Web Token)
- **文档**：Knife4j (Swagger增强版)
- **对象存储**：阿里云 OSS
- **支付**：微信支付 API v3
- **分页**：PageHelper
- **工具类**：Lombok、FastJSON、Apache POI

### 前端技术
- **框架**：Vue.js
- **UI组件**：Element UI
- **构建工具**：Webpack
- **HTTP客户端**：Axios

### 部署环境
- **服务器**：Nginx
- **应用服务器**：Tomcat (内嵌)
- **数据库**：MySQL
- **缓存**：Redis (可选)

## 📋 环境要求

### 开发环境
- **JDK**：1.8+
- **Maven**：3.6+
- **MySQL**：8.0+
- **Node.js**：14.0+ (前端开发)
- **Nginx**：1.18+ (生产部署)

### 开发工具推荐
- **IDE**：IntelliJ IDEA / Eclipse
- **数据库管理**：Navicat / DataGrip
- **API测试**：Postman / Apifox
- **版本控制**：Git

## 🚀 快速开始

### 1. 环境准备

确保您的系统已安装以下软件：
- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- Git

### 2. 克隆项目

```bash
git clone <repository-url>
cd sky-take-out
```

### 3. 数据库配置

#### 创建数据库
```sql
CREATE DATABASE sky_take_out CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 导入数据库脚本
```bash
mysql -u root -p sky_take_out < database/sky.sql
```

### 4. 配置文件修改

编辑 `sky-server/src/main/resources/application-dev.yml`：

```yaml
sky:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    host: localhost
    port: 3306
    database: sky_take_out
    username: root        # 修改为您的数据库用户名
    password: '1234'      # 修改为您的数据库密码
```

### 5. 第三方服务配置（可选）

如需使用完整功能，请配置以下服务：

#### 阿里云OSS配置
```yaml
sky:
  alioss:
    endpoint: your-oss-endpoint
    access-key-id: your-access-key-id
    access-key-secret: your-access-key-secret
    bucket-name: your-bucket-name
```

#### 微信支付配置
```yaml
sky:
  wechat:
    appid: your-wechat-appid
    secret: your-wechat-secret
    mchid: your-merchant-id
    mchSerialNo: your-merchant-serial-no
    privateKeyFilePath: your-private-key-path
    apiV3Key: your-api-v3-key
    weChatPayCertFilePath: your-cert-path
    notifyUrl: your-notify-url
    refundNotifyUrl: your-refund-notify-url
```

### 6. 启动应用

```bash
# 使用 Maven 启动
mvn spring-boot:run -pl sky-server

# 或者使用 IDE 直接运行 SkyApplication.java
```

### 7. 访问应用

- **后端API地址**：http://localhost:8080
- **API文档地址**：http://localhost:8080/doc.html
- **管理端地址**：配合前端项目访问
- **用户端地址**：配合前端项目访问

## 📁 项目结构

```
sky-take-out/
├── database/                    # 数据库脚本
│   └── sky.sql                 # 数据库初始化脚本
├── sky-common/                 # 公共模块
│   ├── src/main/java/com/sky/
│   │   ├── constant/           # 常量定义
│   │   ├── context/            # 上下文工具
│   │   ├── enumeration/        # 枚举类
│   │   ├── exception/          # 异常类
│   │   ├── json/               # JSON处理
│   │   ├── properties/         # 配置属性
│   │   ├── result/             # 响应结果
│   │   └── utils/              # 工具类
│   └── pom.xml
├── sky-pojo/                   # 实体类模块
│   ├── src/main/java/com/sky/
│   │   ├── dto/                # 数据传输对象
│   │   ├── entity/             # 实体类
│   │   └── vo/                 # 视图对象
│   └── pom.xml
├── sky-server/                 # 服务模块
│   ├── src/main/java/com/sky/
│   │   ├── config/             # 配置类
│   │   ├── controller/         # 控制器
│   │   ├── handler/            # 异常处理器
│   │   ├── interceptor/        # 拦截器
│   │   ├── mapper/             # 数据访问层
│   │   ├── service/            # 业务逻辑层
│   │   └── SkyApplication.java # 启动类
│   ├── src/main/resources/
│   │   ├── mapper/             # MyBatis映射文件
│   │   ├── application.yml     # 主配置文件
│   │   └── application-dev.yml # 开发环境配置
│   └── pom.xml
└── pom.xml                     # 根POM文件
```

## 🗄️ 数据库设计

### 核心表结构

| 表名 | 说明 | 主要字段 |
|------|------|----------|
| employee | 员工表 | id, name, username, password, phone, status |
| category | 分类表 | id, type, name, sort, status |
| dish | 菜品表 | id, name, category_id, price, image, status |
| dish_flavor | 菜品口味表 | id, dish_id, name, value |
| setmeal | 套餐表 | id, category_id, name, price, status |
| setmeal_dish | 套餐菜品关联表 | id, setmeal_id, dish_id, copies |
| user | 用户表 | id, openid, name, phone, sex, avatar |
| address_book | 地址簿表 | id, user_id, consignee, phone, address |
| shopping_cart | 购物车表 | id, user_id, dish_id, setmeal_id, number |
| orders | 订单表 | id, number, status, user_id, amount |
| order_detail | 订单明细表 | id, order_id, dish_id, setmeal_id, number |

## 🔧 配置说明

### 应用配置

```yaml
server:
  port: 8080                    # 服务端口

spring:
  profiles:
    active: dev                 # 激活的配置文件
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/sky_take_out
      username: root
      password: 1234

mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.sky.entity
  configuration:
    map-underscore-to-camel-case: true

sky:
  jwt:
    admin-secret-key: itcast    # JWT签名密钥
    admin-ttl: 7200000         # Token过期时间(毫秒)
    admin-token-name: token    # Token名称
```

## 📚 API 文档

启动应用后，访问 http://localhost:8080/doc.html 查看完整的 API 文档。

### 主要接口模块

- **员工管理**：`/admin/employee/*`
- **分类管理**：`/admin/category/*`
- **菜品管理**：`/admin/dish/*`
- **套餐管理**：`/admin/setmeal/*`
- **订单管理**：`/admin/order/*`
- **数据统计**：`/admin/report/*`
- **用户端**：`/user/*`

## 🐛 常见问题

### 1. 数据库连接失败
- 检查MySQL服务是否启动
- 确认数据库连接配置是否正确
- 检查防火墙设置

### 2. JWT Token 过期
- 检查系统时间是否正确
- 确认JWT配置参数
- 重新登录获取新Token

### 3. 文件上传失败
- 检查阿里云OSS配置
- 确认访问权限
- 检查网络连接

### 4. 微信支付异常
- 确认微信支付配置信息
- 检查证书文件路径
- 验证回调地址配置

## 🤝 贡献指南

1. Fork 本仓库
2. 创建您的特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交您的更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开一个 Pull Request

## 📝 更新日志

### v1.0.0 (2024-01-01)
- 初始版本发布
- 实现基础功能模块
- 完成前后端分离架构

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 👨‍💻 作者

- **项目作者** - 苍穹外卖开发团队

## 🙏 致谢

- 感谢 Spring Boot 社区提供的优秀框架
- 感谢阿里云提供的云服务支持
- 感谢所有贡献者的辛勤付出

---

如果您觉得这个项目对您有帮助，请给我们一个 ⭐️！ 