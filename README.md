# UEats Takeout Management System

## Project Overview

The UEats Takeout Management System is a modern food delivery platform developed with Spring Boot, providing comprehensive digital management services for restaurants. The system adopts a front-end and back-end separation architecture, supporting both admin and user applications, and covers the entire business process from dish management to order processing.

## Features

### Admin Features
- **Employee Management**: Maintain employee info, permission control, login authentication
- **Category Management**: CRUD for dish and meal categories
- **Dish Management:**：Manage dish info, configure flavors, upload images
- **Meal Management**：Configure set meals, associate dishes, set prices
- **Order Management**：View orders, update status, statistics
- **Data Analytics**：Business reports, sales charts, user analysis
- **System Settings**：Basic configuration, parameter management

### User Features
- **User Authentication**：Login, user registration
- **Shopping Cart**：Add items, adjust quantity, bulk operations
- **Address Management**：Manage delivery addresses, set default address
- **Order & Payment**：QR / Card Payment, order tracking
- **User Center**：Order history, manage personal info

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
- **Framework**：Vue.js 2.6.10 + TypeScript 3.6.2
- **UI Components**：Element UI 2.12.0
- **HTTP Client**：Axios 0.19.0
- **State Management**：Vuex 3.1.1
- **Routing**：Vue Router 3.1.2
- **Build Tool**：Vue CLI 3.11.0
- **Styling**：Sass/SCSS + Style Resources Loader
- **Charts**：ECharts 5.3.2
- **Testing**：Jest + Cypress
- **PWA Support**：Service Worker

### Deployment
- **Web Server**：Nginx 1.20.2
- **App Server**：Tomcat
- **Database**：MySQL
- **Cache**：Redis (optional)

## Environment Requirements

### Development
- **JDK**：1.8+
- **Maven**：3.6+
- **MySQL**：8.0+
- **Node.js**：12.22.12 (recommended) / 12.x - 14.x
- **npm**：6.x+ or **Yarn**：1.22+

### Recommended Tools
- **IDE**：IntelliJ IDEA (Backend) / VS Code (Frontend)
- **API Testing**：Postman
- **Database Client**：Navicat / DBeaver

## Quick Start

### 1. Backend Setup

#### Prepare Environment
Ensure the following are installed:
- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- Git

#### Clone the Project
```bash
git clone https://github.com/haoo04/UEats.git
cd sky-take-out
```

#### Configure the Database
##### Create Database
```sql
CREATE DATABASE sky_take_out CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

##### Import SQL Script
```bash
mysql -u root -p sky_take_out < database/sky.sql
```

#### Modify Configuration
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
  
  # If you need to use Ali OSS services, please enter your own API key
  alioss:
    endpoint:
    access-key-id:
    access-key-secret:
    bucket-name:

  redis:
    host: localhost
    port: 6379
    password:           # Change to your DB password
    database:           # Change to your selected database,default 0
```
Edit sky-server/src/main/resources/application.yml:

```yaml
file:
# The image folder under your own project path
upload-path: [your folder path]
```

#### Start the Backend
```bash
# Use Maven to start
mvn spring-boot:run -pl sky-server

# Or run SkyApplication.java in your IDE
```

### 2. Frontend Setup

#### Environment Requirements
- **Node.js**: v12.22.12 (recommended for this project)
- **npm**: 6.14+ or **Yarn**: 1.22+

#### Install Dependencies
```bash
# Navigate to frontend directory
cd project-UEats-admin-vue-ts

# Install dependencies using npm (recommended for Node.js v12.22.12)
npm install

# Or using Yarn
yarn install
```

#### Configure API Base URL
Edit `src/config.json` to set the backend API address:
```json
{
  "baseUrl": "http://localhost:8080"
}
```

#### Environment Variables (Optional)
Create `.env.development` file in the frontend root directory:
```env
# Development Environment
NODE_ENV=development
VUE_APP_URL=http://localhost:8080
VUE_APP_TITLE=UEats Admin Portal
```

#### Start Development Server
```bash
# Start frontend development server
npm run serve

# Or using Yarn
yarn serve
```

The frontend development server will start on **http://localhost:8081**

#### Build for Production
```bash
# Production build
npm run build

# UAT/Staging build
npm run build:uat
```
### Or put the static resource in nginx-1.20.2 in your own nginx folder

### 3. Access the System

- **Frontend Admin Portal**：http://localhost:8081
- **Backend API**：http://localhost:8080
- **API Documentation**：http://localhost:8080/doc.html

#### Default Admin Credentials
- **Username**: admin
- **Password**: 123456

## 📁 Project Structure

```
sky-take-out/
├── database/                           # Database scripts
│   └── sky.sql                        # Initialization script
├── project-UEats-admin-vue-ts/        # Frontend Admin Portal
│   ├── public/                        # Static assets
│   │   ├── favicon.ico               # Favicon
│   │   ├── index.html               # HTML template
│   │   └── img/                     # Static images
│   ├── src/                          # Source code
│   │   ├── api/                     # API request modules
│   │   ├── assets/                  # Static assets (processed by webpack)
│   │   ├── components/              # Reusable Vue components
│   │   ├── icons/                   # SVG icon components
│   │   ├── layout/                  # Layout components
│   │   ├── router.ts                # Vue Router configuration
│   │   ├── store/                   # Vuex store modules
│   │   ├── styles/                  # Global styles (SCSS)
│   │   ├── utils/                   # Utility functions
│   │   ├── views/                   # Page components
│   │   ├── App.vue                  # Root component
│   │   ├── main.ts                  # Application entry point
│   │   └── config.json              # Frontend configuration
│   ├── package.json                 # Dependencies and scripts
│   ├── tsconfig.json               # TypeScript configuration
│   ├── vue.config.js               # Vue CLI configuration
│   └── README.md                   # Frontend documentation
├── sky-common/                      # Common module
│   ├── src/main/java/com/sky/
│   │   ├── constant/               # Constants
│   │   ├── context/                # Contextual Tools
│   │   ├── enumeration/            # Enums
│   │   ├── exception/              # Exception Class
│   │   ├── json/                   # JSON Processing
│   │   ├── properties/             # Configuration properties
│   │   ├── result/                 # Response results
│   │   └── utils/                  # utils
│   └── pom.xml
├── sky-pojo/                       # Entity module
│   ├── src/main/java/com/sky/
│   │   ├── dto/                    # Data Transfer Objects
│   │   ├── entity/                 # Entity Class
│   │   └── vo/                     # View Object
│   └── pom.xml
├── sky-server/                     # Service module
│   ├── src/main/java/com/sky/
│   │   ├── config/                 # Configuration Class
│   │   ├── controller/             # Controller
│   │   ├── handler/                # Exception Handler
│   │   ├── interceptor/            # Interceptor
│   │   ├── mapper/                 # Data Access Layer
│   │   ├── service/                # Business logic layer
│   │   └── SkyApplication.java     # Startup 
│   ├── src/main/resources/
│   │   ├── mapper/                 # MyBatis Mapping File
│   │   ├── application.yml         # Main configuration file
│   │   └── application-dev.yml     # Development environment configuration
│   └── pom.xml
└── pom.xml                         # Root POM file
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

## Configuration Notes

### Backend Configuration

```yaml
server:
  port: 8080                    # Backend port

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

### Frontend Configuration

The frontend development server (`vue.config.js`) is configured to:
- Run on port **8081**
- Proxy API requests to backend (localhost:8080)
- Enable hot module replacement
- Support TypeScript compilation
- Include SCSS preprocessing

```javascript
// vue.config.js
module.exports = {
  devServer: {
    host: '0.0.0.0',
    port: 8081,
    open: true,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        pathRewrite: { '^/api': '' }
      }
    }
  }
}
```

## Troubleshooting

### Frontend Issues

#### Node.js Version Compatibility
- **Recommended**: Node.js v12.22.12
- **Compatible**: Node.js 12.x - 14.x
- **Not Compatible**: Node.js 16+ (may cause build issues with Vue CLI 3.x)

#### Common Commands
```bash
# Clear npm cache
npm cache clean --force

# Remove node_modules and reinstall
rm -rf node_modules package-lock.json
npm install

# Check Node.js and npm versions
node --version  # Should show v12.22.12
npm --version

# Fix potential peer dependency issues
npm install --legacy-peer-deps
```

#### Development Server Issues
```bash
# If port 8081 is occupied
npm run serve -- --port 8082

# Fix ESLint issues
npm run lint -- --fix
```

### Backend Issues

#### Database Connection
- Ensure MySQL is running on port 3306
- Verify database credentials in `application-dev.yml`
- Check if database `sky_take_out` exists

#### Port Conflicts
- Backend default port: 8080
- Frontend development port: 8081
- Ensure both ports are available

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

## Development Workflow

### Typical Development Process
1. **Start Backend**: `mvn spring-boot:run -pl sky-server`
2. **Start Frontend**: `cd project-UEats-admin-vue-ts && npm run serve`
3. **Access Admin Portal**: http://localhost:8081
4. **API Testing**: http://localhost:8080/doc.html
5. **Code Changes**: Hot reload enabled for both frontend and backend

### Building for Production
```bash
# Backend: Create JAR file
mvn clean package -pl sky-server

# Frontend: Create dist folder
cd project-UEats-admin-vue-ts
npm run build
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

If you find this project helpful, please give us a ⭐️!
