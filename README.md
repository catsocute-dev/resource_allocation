# Resource Allocation System (ERP)

Hệ thống quản lý phân bổ nguồn lực dự án, giúp theo dõi và quản lý nhân viên, dự án, và việc phân bổ nhân sự vào các dự án.

## 📋 Mục lục

- [Tổng quan](#tổng-quan)
- [Công nghệ sử dụng](#công-nghệ-sử-dụng)
- [Cấu trúc dự án](#cấu-trúc-dự-án)
- [Quy tắc code (Code Regulations)](#quy-tắc-code-code-regulations)
- [Xử lý ngoại lệ (Exception Handling)](#xử-lý-ngoại-lệ-exception-handling)
- [Business Logic](#business-logic)
- [API Documentation](#api-documentation)
- [Cấu hình](#cấu-hình)
- [Hướng dẫn chạy dự án](#hướng-dẫn-chạy-dự-án)

---

## Tổng quan

Resource Allocation System là một ứng dụng web backend được xây dựng bằng Spring Boot, cung cấp các chức năng:

- **Quản lý nhân viên (Employee)**: CRUD thông tin nhân viên
- **Quản lý dự án (Project)**: CRUD thông tin dự án với trạng thái (PLANNING, ACTIVE, COMPLETED)
- **Phân bổ nguồn lực (Allocation)**: Quản lý việc phân bổ nhân viên vào dự án với phần trăm allocation
- **Báo cáo (Reports)**: 
  - Workload của nhân viên
  - Utilization report
  - Tìm kiếm nhân viên available
  - Tìm kiếm nhân viên overloaded
- **Xác thực & Phân quyền (Identity)**: JWT-based authentication với RBAC

---

## Công nghệ sử dụng

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21 | Programming language |
| Spring Boot | 4.1.0 | Application framework |
| Spring Security | OAuth2 Resource Server | Authentication & Authorization |
| Spring Data JPA | - | Data access layer |
| PostgreSQL | - | Database |
| Flyway | - | Database migration |
| Lombok | 1.18.32 | Boilerplate reduction |
| MapStruct | 1.6.3 | Object mapping |
| SpringDoc OpenAPI | 2.8.0 | API documentation (Swagger) |
| Spring AI (Google GenAI) | - | AI integration |
| Cloudinary | 1.39.0 | File upload |

---

## Cấu trúc dự án

```
src/main/java/com/fsoft/erp/
├── ErpApplication.java                    # Main application class
│
├── common/                                # Common/shared components
│   ├── annotation/
│   │   └── LogAllocation.java             # Custom annotation for audit logging
│   ├── aspect/
│   │   └── AllocationLogAspect.java       # AOP aspect for allocation logging
│   ├── config/
│   │   ├── CorsConfig.java                # CORS configuration
│   │   ├── JwtAuthenticationEntryPoint.java # JWT entry point
│   │   ├── OpenApiConfig.java             # Swagger/OpenAPI configuration
│   │   └── SecurityConfig.java            # Spring Security configuration
│   ├── constant/
│   │   ├── ApiConstant.java               # API response constants
│   │   ├── GlobalVariableConstant.java    # Global variables
│   │   ├── InitializerOrder.java          # Initialization order
│   │   ├── JwtClaimSetConstant.java       # JWT claim constants
│   │   └── PaginationConstant.java        # Pagination constants
│   ├── dto/
│   │   ├── ErrorMessage.java              # Error message DTO
│   │   ├── request/
│   │   │   ├── PagingRequest.java         # Pagination request
│   │   │   └── SortRequest.java           # Sort request
│   │   └── response/
│   │       ├── ApiResponse.java           # Standard API response wrapper
│   │       └── PagingResponse.java        # Paginated response
│   ├── entity/
│   │   └── BaseEntity.java                # Base entity with common fields
│   ├── enums/
│   │   └── ErrorCode.java                 # Error code enumeration
│   ├── exception/
│   │   ├── AppException.java              # Custom application exception
│   │   └── GlobalExceptionHandler.java    # Global exception handler
│   ├── properties/
│   │   └── AdminProperties.java           # Admin configuration properties
│   └── utils/
│       └── PagingUtil.java                # Pagination utility
│
├── modules/
│   ├── erp/                               # ERP module (core business)
│   │   ├── constants/
│   │   │   ├── AllocationConstants.java
│   │   │   ├── AllocationErrorCodeConstants.java
│   │   │   ├── EmployeeConstants.java
│   │   │   ├── EmployeeErrorCodeConstants.java
│   │   │   ├── ProjectConstants.java
│   │   │   └── ProjectErrorCodeConstants.java
│   │   ├── controller/
│   │   │   ├── AllocationController.java
│   │   │   ├── EmployeeController.java
│   │   │   └── ProjectController.java
│   │   ├── dto/
│   │   │   ├── request/
│   │   │   │   ├── AllocationCreationRequest.java
│   │   │   │   ├── AllocationUpdateRequest.java
│   │   │   │   ├── EmployeeCreationRequest.java
│   │   │   │   └── ProjectCreationRequest.java
│   │   │   └── response/
│   │   │       ├── AllocationResponse.java
│   │   │       ├── EmployeeResponse.java
│   │   │       ├── ProjectResponse.java
│   │   │       ├── UtilizationResponse.java
│   │   │       └── WorkloadResponse.java
│   │   ├── entity/
│   │   │   ├── Allocation.java
│   │   │   ├── Employee.java
│   │   │   └── Project.java
│   │   ├── enums/
│   │   │   └── ProjectStatus.java
│   │   ├── mapper/
│   │   │   ├── AllocationMapper.java
│   │   │   ├── EmployeeMapper.java
│   │   │   └── ProjectMapper.java
│   │   ├── repository/
│   │   │   ├── AllocationRepository.java
│   │   │   ├── EmployeeRepository.java
│   │   │   └── ProjectRepository.java
│   │   └── service/
│   │       ├── interfaces/
│   │       │   ├── AllocationService.java
│   │       │   ├── EmployeeService.java
│   │       │   └── ProjectService.java
│   │       └── impl/
│   │           ├── AllocationServiceImpl.java
│   │           ├── EmployeeServiceImpl.java
│   │           └── ProjectServiceImpl.java
│   │
│   └── identity/                          # Identity module (auth & user management)
│       ├── constants/
│       │   ├── permission/
│       │   │   ├── PermissionConstants.java
│       │   │   └── StartDefinedPermission.java
│       │   ├── role/
│       │   │   ├── PredefinedRole.java
│       │   │   ├── RoleConstants.java
│       │   │   └── RoleControllerConstants.java
│       │   └── user/
│       │       ├── UserConstants.java
│       │       ├── UserControllerConstants.java
│       │       ├── UserErrorCodeConstants.java
│       │       └── UserMetadataConstants.java
│       ├── controller/
│       │   ├── AuthenticationController.java
│       │   ├── RoleController.java
│       │   └── UserController.java
│       ├── dto/
│       │   ├── request/
│       │   │   ├── AuthenticationRequest.java
│       │   │   ├── IntrospectRequest.java
│       │   │   ├── RoleUpdateRequest.java
│       │   │   ├── UserCreationRequest.java
│       │   │   └── UserRolesUpdateRequest.java
│       │   └── response/
│       │       ├── AuthenticationResponse.java
│       │       ├── IntrospectResponse.java
│       │       ├── RoleResponse.java
│       │       └── UserResponse.java
│       ├── entity/
│       │   ├── Permission.java
│       │   ├── Role.java
│       │   └── User.java
│       ├── enums/
│       │   └── UserStatus.java
│       ├── mapper/
│       │   ├── RoleMapper.java
│       │   └── UserMapper.java
│       ├── repository/
│       │   ├── PermissionRepository.java
│       │   ├── RoleRepository.java
│       │   └── UserRepository.java
│       └── service/
│           ├── interfaces/
│           │   ├── AuthenticationService.java
│           │   ├── RoleService.java
│           │   └── UserService.java
│           └── impl/
│               ├── AuthenticationServiceImpl.java
│               ├── RoleServiceImpl.java
│               └── UserServiceImpl.java
```

---

## Quy tắc code (Code Regulations)

### 1. Package Structure
- **Modular architecture**: Chia theo feature module (`erp`, `identity`)
- **Layer separation**: `controller` → `service` → `repository` → `entity`
- **DTO pattern**: Tách biệt request/response objects

### 2. Naming Conventions
- **Entity**: PascalCase, số nhiều cho table name (`users`, `roles`, `permissions`)
- **Constants**: UPPER_SNAKE_CASE, đặt trong class constants riêng
- **DTO**: `{Entity}CreationRequest`, `{Entity}Response`
- **Service**: Interface + Implementation pattern
- **Repository**: `{Entity}Repository` extends `JpaRepository`

### 3. Lombok Usage
```java
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseEntity {
    // fields
}
```

### 4. Constructor Injection
Sử dụng `@RequiredArgsConstructor` với `final` fields:
```java
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeController {
    EmployeeService employeeService;
}
```

### 5. API Response Wrapper
Tất cả API responses đều được wrap trong `ApiResponse<T>`:
```java
ApiResponse<EmployeeResponse> response = ApiResponse.<EmployeeResponse>builder()
        .success(ApiConstant.SUCCESS)
        .data(employeeService.create(request))
        .build();
```

### 6. Validation
Sử dụng Jakarta Validation annotations:
```java
@Column(name = EmployeeConstants.COL_EMPLOYEE_CODE, nullable = false, unique = true)
@Size(max = EmployeeConstants.MAX_EMPLOYEE_CODE)
String employeeCode;
```

---

## Xử lý ngoại lệ (Exception Handling)

### Global Exception Handler
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Handles RuntimeException
    // Handles AppException (custom business exceptions)
    // Handles AccessDeniedException
    // Handles MethodArgumentNotValidException
}
```

### Error Code System
```java
public enum ErrorCode {
    // AUTHENTICATION ERRORS (AUTH_xxxx)
    AUTH_UNAUTHENTICATED("AUTH_1000", "Unauthenticated", HttpStatus.UNAUTHORIZED),
    
    // USER ERRORS (USER_xxxx)
    USER_NOT_FOUND("USER_1004", "User not found", HttpStatus.NOT_FOUND),
    
    // EMPLOYEE ERRORS (EMP_xxxx)
    EMPLOYEE_NOT_FOUND("EMP_1000", "Employee not found", HttpStatus.NOT_FOUND),
    
    // PROJECT ERRORS (PROJ_xxxx)
    PROJECT_NOT_FOUND("PROJ_1000", "Project not found", HttpStatus.NOT_FOUND),
    
    // ALLOCATION ERRORS (ALLOC_xxxx)
    ALLOCATION_EXCEEDED("ALLOC_1001", "Employee allocation exceeds 100%", HttpStatus.BAD_REQUEST),
    // ...
}
```

### Error Response Format
```json
{
    "success": false,
    "errorMessage": {
        "errorCode": "EMP_1000",
        "message": "Employee not found"
    },
    "data": null
}
```

---

## Business Logic

### 1. Employee Management
- Tạo mới nhân viên với employee code, tên, email, role, department
- Validate unique constraints (employee_code, email)
- Phân trang và sắp xếp danh sách

### 2. Project Management
- Tạo dự án với trạng thái: `PLANNING`, `ACTIVE`, `COMPLETED`
- Theo dõi thời gian bắt đầu/kết thúc
- Phân trang và sắp xếp

### 3. Allocation Management
- **Tạo allocation**: Phân bổ nhân viên vào dự án với % allocation
- **Validate business rules**:
  - Tổng allocation của nhân viên không được vượt quá 100%
  - Không được phân bổ vào dự án đã COMPLETED
  - Allocation percent phải từ 1-100
- **Update allocation**: Cập nhật % hoặc role trong dự án
- **Delete allocation**: Xóa phân bổ

### 4. Reports & Analytics
- **Employee Workload**: Xem tổng % allocation của một nhân viên
- **Utilization Report**: Báo cáo利用率 của tất cả nhân viên
- **Available Resources**: Tìm nhân viên có allocation < 100%
- **Overloaded Employees**: Tìm nhân viên có allocation = 100%

### 5. Audit Logging
Sử dụng AOP Aspect để log tất cả operations liên quan đến Allocation:
```java
@LogAllocation(operation = "CREATE")
public AllocationResponse create(AllocationCreationRequest request) {
    // implementation
}
```

---

## API Documentation

> **Swagger UI**: http://localhost:8080/swagger-ui  
> **API Docs**: http://localhost:8080/api/v1/api-docs

### Authentication Module (`/api/v1/auth`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/token` | Đăng nhập, lấy JWT token | ❌ |
| POST | `/introspect` | Kiểm tra tính hợp lệ của token | ❌ |

### User Module (`/api/v1/users`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/` | Tạo user mới | ❌ |
| GET | `/` | Lấy danh sách users (phân trang) | ✅ |
| GET | `/me` | Lấy thông tin user hiện tại | ✅ |
| PUT | `/{userId}/roles` | Cập nhật roles cho user | ✅ |
| DELETE | `/{userId}` | Xóa user | ✅ |

### Role Module (`/api/v1/roles`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/` | Lấy danh sách roles (phân trang) | ✅ |
| GET | `/{roleId}` | Lấy role theo ID | ✅ |
| PUT | `/{roleId}` | Cập nhật role | ✅ |
| DELETE | `/{roleId}` | Xóa role | ✅ |

### Employee Module (`/api/v1/employees`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/` | Tạo nhân viên mới | ✅ |
| GET | `/` | Lấy danh sách nhân viên (phân trang) | ✅ |
| GET | `/{id}` | Lấy nhân viên theo ID | ✅ |

### Project Module (`/api/v1/projects`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/` | Tạo dự án mới | ✅ |
| GET | `/` | Lấy danh sách dự án (phân trang) | ✅ |
| GET | `/{id}` | Lấy dự án theo ID | ✅ |

### Allocation Module (`/api/v1`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/allocations` | Tạo allocation mới | ✅ |
| PUT | `/allocations/{id}` | Cập nhật allocation | ✅ |
| DELETE | `/allocations/{id}` | Xóa allocation | ✅ |
| GET | `/employees/{id}/workload` | Xem workload nhân viên | ✅ |
| GET | `/reports/utilization` | Báo cáo utilization | ✅ |
| GET | `/reports/available` | Tìm nhân viên available | ✅ |
| GET | `/reports/overloaded` | Tìm nhân viên overloaded | ✅ |

### Pagination Parameters
```
?page=1&size=10&direction=DESC&field=createdAt
```

### Authentication Header
```
Authorization: Bearer <JWT_TOKEN>
```

---

## Cấu hình

### Environment Variables (.env)
```properties
# Server
SERVER_PORT=8080

# Database
DB_URL=jdbc:postgresql://localhost:5432/resource_allocation
DB_USERNAME=postgres
DB_PASSWORD=your_password

# JWT
JWT_SIGNER_KEY=your_secret_key_here

# Admin
ADMIN_USER_NAME=admin
ADMIN_PASSWORD=admin_password

# Google AI (optional)
GOOGLE_API_KEY=your_google_ai_key

# Cloudinary (optional)
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret
```

### Application Properties
- **Virtual Threads**: Enabled for better concurrency
- **JPA**: Hibernate with PostgreSQL dialect
- **Flyway**: Database migration enabled
- **Swagger**: Enabled at `/swagger-ui`

---

## Database Schema

### Tables
1. **employee** - Thông tin nhân viên
2. **project** - Thông tin dự án
3. **allocation** - Phân bổ nhân viên vào dự án
4. **users** - Tài khoản người dùng
5. **roles** - Vai trò
6. **permissions** - Quyền hạn
7. **user_roles** - Quan hệ user-role (many-to-many)
8. **role_permissions** - Quan hệ role-permission (many-to-many)

### Entity Relationships
```
User *──* Role *──* Permission
Employee *──* Project (through Allocation)
```

---

## Hướng dẫn chạy dự án

### Prerequisites
- Java 21+
- PostgreSQL 15+
- Maven 3.8+

### Steps

1. **Clone repository**
```bash
git clone <repository-url>
cd resource_allocation
```

2. **Tạo database**
```sql
CREATE DATABASE resource_allocation;
```

3. **Tạo file `.env`**
```bash
cp .env.example .env
# Edit .env with your configuration
```

4. **Chạy Flyway migration** (nếu có)
```bash
mvn flyway:migrate
```

5. **Build và chạy**
```bash
mvn clean install
mvn spring-boot:run
```

6. **Truy cập**
- Application: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui
- API Docs: http://localhost:8080/api/v1/api-docs

---

## Development

### Adding New Entity
1. Tạo entity class extends `BaseEntity`
2. Tạo constants class cho table/column names
3. Tạo DTO request/response
4. Tạo Mapper interface (MapStruct)
5. Tạo Repository interface
6. Tạo Service interface + implementation
7. Tạo Controller

### Code Review Checklist
- [ ] Sử dụng constants cho table/column names
- [ ] Validate annotations đầy đủ
- [ ] Xử lý exception bằng ErrorCode
- [ ] Log allocation operations với `@LogAllocation`
- [ ] Phân trang cho list endpoints

---

## License

Internal project - FPT Software

---

## Contact

- **Author**: catsocute
- **Email**: [your-email@fpt.com]
