-- =============================================================================
-- Resource Allocation System - Database Schema
-- Generated from JPA Entity definitions
-- =============================================================================

-- =============================================================================
-- ERP Module Tables
-- =============================================================================

-- Employee table
CREATE TABLE employee (
    id VARCHAR(36) PRIMARY KEY DEFAULT gen_random_uuid()::text,
    version BIGINT NOT NULL DEFAULT 0,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    employee_code VARCHAR(20) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role VARCHAR(50),
    department VARCHAR(50),

    CONSTRAINT uk_employee_code UNIQUE (employee_code),
    CONSTRAINT uk_employee_email UNIQUE (email)
);

-- Project table
CREATE TABLE project (
    id VARCHAR(36) PRIMARY KEY DEFAULT gen_random_uuid()::text,
    version BIGINT NOT NULL DEFAULT 0,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    project_code VARCHAR(20) NOT NULL,
    project_name VARCHAR(200) NOT NULL,
    customer VARCHAR(100),
    start_date DATE,
    end_date DATE,
    status VARCHAR(20) NOT NULL,

    CONSTRAINT uk_project_code UNIQUE (project_code),
    CONSTRAINT chk_project_status CHECK (status IN ('PLANNING', 'ACTIVE', 'COMPLETED'))
);

-- Allocation table (junction table with attributes)
CREATE TABLE allocation (
    id VARCHAR(36) PRIMARY KEY DEFAULT gen_random_uuid()::text,
    version BIGINT NOT NULL DEFAULT 0,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    employee_id VARCHAR(36) NOT NULL,
    project_id VARCHAR(36) NOT NULL,
    allocation_percent INTEGER NOT NULL,
    role_in_project VARCHAR(100),
    start_date DATE,
    end_date DATE,

    CONSTRAINT fk_allocation_employee FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE,
    CONSTRAINT fk_allocation_project FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE,
    CONSTRAINT chk_allocation_percent CHECK (allocation_percent >= 1 AND allocation_percent <= 100)
);

-- =============================================================================
-- Identity Module Tables
-- =============================================================================

-- Permissions table
CREATE TABLE permissions (
    id VARCHAR(36) PRIMARY KEY DEFAULT gen_random_uuid()::text,
    version BIGINT NOT NULL DEFAULT 0,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    permission_name VARCHAR(100) NOT NULL,

    CONSTRAINT uk_permission_name UNIQUE (permission_name)
);

-- Roles table
CREATE TABLE roles (
    id VARCHAR(36) PRIMARY KEY DEFAULT gen_random_uuid()::text,
    version BIGINT NOT NULL DEFAULT 0,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    role_name VARCHAR(50) NOT NULL,

    CONSTRAINT uk_role_name UNIQUE (role_name)
);

-- Role-Permissions junction table
CREATE TABLE role_permissions (
    role_id VARCHAR(36) NOT NULL,
    permission_id VARCHAR(36) NOT NULL,

    CONSTRAINT pk_role_permissions PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_role_permissions_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    CONSTRAINT fk_role_permissions_permission FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

-- Users table
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY DEFAULT gen_random_uuid()::text,
    version BIGINT NOT NULL DEFAULT 0,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT uk_users_username UNIQUE (username),
    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT chk_users_status CHECK (status IN ('ACTIVE', 'BLOCKED', 'DELETED'))
);

-- User-Roles junction table
CREATE TABLE user_roles (
    user_id VARCHAR(36) NOT NULL,
    role_id VARCHAR(36) NOT NULL,

    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- =============================================================================
-- Indexes for better query performance
-- =============================================================================

-- Employee indexes
CREATE INDEX idx_employee_code ON employee(employee_code);
CREATE INDEX idx_employee_email ON employee(email);
CREATE INDEX idx_employee_department ON employee(department);

-- Project indexes
CREATE INDEX idx_project_code ON project(project_code);
CREATE INDEX idx_project_status ON project(status);
CREATE INDEX idx_project_dates ON project(start_date, end_date);

-- Allocation indexes
CREATE INDEX idx_allocation_employee ON allocation(employee_id);
CREATE INDEX idx_allocation_project ON allocation(project_id);
CREATE INDEX idx_allocation_dates ON allocation(start_date, end_date);

-- User indexes
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_status ON users(status);

-- =============================================================================
-- Comments for documentation
-- =============================================================================

COMMENT ON TABLE employee IS 'Stores employee information';
COMMENT ON TABLE project IS 'Stores project information';
COMMENT ON TABLE allocation IS 'Stores employee-project allocation with percentage';
COMMENT ON TABLE permissions IS 'Stores system permissions';
COMMENT ON TABLE roles IS 'Stores user roles';
COMMENT ON TABLE role_permissions IS 'Junction table for role-permission many-to-many relationship';
COMMENT ON TABLE users IS 'Stores user account information';
COMMENT ON TABLE user_roles IS 'Junction table for user-role many-to-many relationship';
