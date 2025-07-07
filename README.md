# UEats Takeout Management System

## Project Overview

The UEats Takeout Management System is a modern food delivery platform developed with Spring Boot, providing comprehensive digital management services for restaurants. The system adopts a front-end and back-end separation architecture, supporting both admin and user applications, and covers the entire business process from dish management to order processing.

## Features

### Admin Features
- ** Employee Management**: Maintain employee info, permission control, login authentication
- ** Category Management**: CRUD for dish and meal categories
- ** Dish Management:**：Manage dish info, configure flavors, upload images
- ** Meal Management**：Configure set meals, associate dishes, set prices
- ** Order Management**：View orders, update status, statistics
- ** Data Analytics**：Business reports, sales charts, user analysis
- ** System Settings**：Basic configuration, parameter management

### User Features
- ** User Authentication**：WeChat login, user registration
- ** Shopping Cart**：Add items, adjust quantity, bulk operations
- ** Address Management**：Manage delivery addresses, set default address
- ** Order & Payment**：WeChat Pay, order tracking
- ** User Center**：Order history, manage personal info

## Tech Stack

### Backend
- **Framework**：Spring Boot 2.7.3
- **Database**：MySQL 8.0+
- **ORM**：MyBatis + MyBatis-Plus
- **Connection Pool**：Druid
- **Authentication**：JWT (JSON Web Token)
- **API Docs**：Knife4j (enhanced Swagger)
- **Pagination**：PageHelper
- **Utilities**：Lombok、FastJSON、Apache POI

### Frontend
- **Framework**：Vue.js
- **UI Components**：Element UI
- **HTTP Client**：Axios

### Deployment
- **Web Server**：Nginx
- **App Server**：Tomcat
- **Database**：MySQL
- **Database**：Redis (optional)

## Environment Requirements

### Development
- **JDK**：1.8+
- **Maven**：3.6+
- **MySQL**：8.0+
- **Node.js**：14.0+ (frontend)
- **Nginx**：1.18+ (production)

### Recommended Tools
- **IDE**：IntelliJ IDEA
- **API Testing**：Postman 

## Quick Start

### 1. Prepare Environment

Ensure the following are installed:
- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- Git

### 2. Clone the Project

```bash
git clone https://github.com/haoo04/UEats.git
```

### 3. Configure the Database

#### Create Database
```sql
CREATE DATABASE sky_take_out CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### Import SQL Script
```bash
mysql -u root -p sky_take_out < database/sky.sql
```

### 4. Modify Configuration

Edit sky-server/src/main/resources/application-dev.yml:

```yaml
sky:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    host: localhost
    port: 3306
    database: sky_take_out
    username: root        # Change to your DB username
    password: '1234'      # Change to your DB password
```

### 5. Configure Third-Party Services (Optional)

如需使用完整功能，请配置以下服务：

#### WeChat Pay
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

### 6. Start the Application

```bash
# Use Maven to start
mvn spring-boot:run -pl sky-server

# Or run SkyApplication.java in your IDE
```

### 7. Access the System

- **Backend API**：http://localhost:8080
- **API Docs**：http://localhost:8080/doc.html

## 📁 Project Structure

```
sky-take-out/
├── database/                    # Database scripts
│   └── sky.sql                 # Initialization script
├── sky-common/                 # Common module
│   ├── src/main/java/com/sky/
│   │   ├── constant/           # Constants
│   │   ├── context/            # Contextual Tools
│   │   ├── enumeration/        # Enums
│   │   ├── exception/          # Exception Class
│   │   ├── json/               # JSON Processing
│   │   ├── properties/         # Configuration properties
│   │   ├── result/             # Response results
│   │   └── utils/              # utils
│   └── pom.xml
├── sky-pojo/                   # Entity module
│   ├── src/main/java/com/sky/
│   │   ├── dto/                # Data Transfer Objects
│   │   ├── entity/             # Entity Class
│   │   └── vo/                 # View Object
│   └── pom.xml
├── sky-server/                 # Service module
│   ├── src/main/java/com/sky/
│   │   ├── config/             # Configuration Class
│   │   ├── controller/         # Controller
│   │   ├── handler/            # Exception Handler
│   │   ├── interceptor/        # Interceptor
│   │   ├── mapper/             # Data Access Layer
│   │   ├── service/            # Business logic layer
│   │   └── SkyApplication.java # Startup 
│   ├── src/main/resources/
│   │   ├── mapper/             # MyBatis Mapping File
│   │   ├── application.yml     # Main configuration file
│   │   └── application-dev.yml # Development environment configuration
│   └── pom.xml
└── pom.xml                     # Root POM file
```

## Database Design

| Table | Description | Fields |
|------|------|----------|
| employee | Employee table | id, name, username, password, phone, status |
| category | Category table | id, type, name, sort, status |
| dish | Dish table | id, name, category_id, price, image, status |
| dish_flavor | Dish flavor table | id, dish_id, name, value |
| setmeal | Set meal table | id, category_id, name, price, status |
| setmeal_dish | Meal-dish relation | id, setmeal_id, dish_id, copies |
| user | 	User table | id, openid, name, phone, sex, avatar |
| address_book | Address table | id, user_id, consignee, phone, address |
| shopping_cart | Cart table | id, user_id, dish_id, setmeal_id, number |
| orders | Order table | id, number, status, user_id, amount |
| order_detail | Order detail table | id, order_id, dish_id, setmeal_id, number |

## 🔧 Configuration Notes

### Application Settings

```yaml
server:
  port: 8080                    # port

spring:
  profiles:
    active: dev                 
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
    admin-secret-key: itcast    # JWT Key
    admin-ttl: 7200000         # Token expiration time (milliseconds)
    admin-token-name: token    # Token Name
```

## API Documentation

After starting the application, visit: http://localhost:8080/doc.html to view the complete API documentation.

### Key API Modules

- **Employee Management**：`/admin/employee/*`
- **Category Management**：`/admin/category/*`
- **Dish Management**：`/admin/dish/*`
- **Meal Management**：`/admin/setmeal/*`
- **Order Management**：`/admin/order/*`
- **Reports**：`/admin/report/*`
- **User APIs**：`/user/*`


## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

If you find this project helpful, please give us a ⭐️!
