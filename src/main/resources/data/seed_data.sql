-- ============================================================
-- ERP Resource Allocation - Seed Data
-- Covers all test cases from erp_spec.md
-- ============================================================

-- ============================================================
-- 1. EMPLOYEES
-- ============================================================
-- Covers: basic CRUD, different departments, roles

INSERT INTO employee (id, employee_code, full_name, email, role, department, created_at, updated_at, version)
VALUES
    -- Fully utilized employee (100%)
    ('e0000001-0000-0000-0000-000000000001', 'EMP001', 'Tuan Ho Anh', 'tuanha@company.com', 'Senior Developer', 'FSOFT-Q1', NOW(), NOW(), 0),

    -- Overloaded employee (>90%, exactly 95%)
    ('e0000001-0000-0000-0000-000000000002', 'EMP002', 'Nam Nguyen Van', 'namnv@company.com', 'Tech Lead', 'FSOFT-Q1', NOW(), NOW(), 0),

    -- Available employee (80% utilized, 20% available)
    ('e0000001-0000-0000-0000-000000000003', 'EMP003', 'Linh Tran Thi', 'lintt@company.com', 'Frontend Developer', 'FSOFT-Q2', NOW(), NOW(), 0),

    -- Low utilization employee (40% utilized, 60% available)
    ('e0000001-0000-0000-0000-000000000004', 'EMP004', 'Hung Le Van', 'hunglv@company.com', 'Backend Developer', 'FSOFT-Q2', NOW(), NOW(), 0),

    -- Employee with no allocation (0%, fully available)
    ('e0000001-0000-0000-0000-000000000005', 'EMP005', 'Mai Pham Thi', 'maipt@company.com', 'QA Engineer', 'FSOFT-Q3', NOW(), NOW(), 0),

    -- Employee allocated to exactly 100% across 2 projects (valid case from spec)
    ('e0000001-0000-0000-0000-000000000006', 'EMP006', 'Duc Tran Minh', 'ductm@company.com', 'Fullstack Developer', 'FSOFT-Q1', NOW(), NOW(), 0),

    -- Employee for testing update scenario
    ('e0000001-0000-0000-0000-000000000007', 'EMP007', 'Hoa Nguyen Thi', 'hoant@company.com', 'DevOps Engineer', 'FSOFT-Q3', NOW(), NOW(), 0);


-- ============================================================
-- 2. PROJECTS
-- ============================================================
-- Covers: PLANNING, ACTIVE, COMPLETED statuses

INSERT INTO project (id, project_code, project_name, customer, start_date, end_date, status, created_at, updated_at, version)
VALUES
    -- Active project
    ('p0000001-0000-0000-0000-000000000001', 'NCG', 'NCG Banking System', 'NCG Bank', '2025-01-01', '2025-12-31', 'ACTIVE', NOW(), NOW(), 0),

    -- Active project
    ('p0000001-0000-0000-0000-000000000002', 'GRID', 'GRID E-commerce Platform', 'GRID Corp', '2025-03-01', '2025-09-30', 'ACTIVE', NOW(), NOW(), 0),

    -- Active project (Internal)
    ('p0000001-0000-0000-0000-000000000003', 'INTAI', 'Internal AI Tool', 'Internal', '2025-06-01', '2025-12-31', 'ACTIVE', NOW(), NOW(), 0),

    -- PLANNING project (should accept allocations)
    ('p0000001-0000-0000-0000-000000000004', 'CLOUD', 'Cloud Migration Project', 'TechCorp', '2026-01-01', '2026-06-30', 'PLANNING', NOW(), NOW(), 0),

    -- COMPLETED project (Business Rule 3: cannot allocate to this)
    ('p0000001-0000-0000-0000-000000000005', 'OLDPRJ', 'Legacy System Migration', 'OldClient', '2024-01-01', '2024-12-31', 'COMPLETED', NOW(), NOW(), 0);


-- ============================================================
-- 3. ALLOCATIONS
-- ============================================================
-- Covers all business rules and report scenarios

INSERT INTO allocation (id, employee_id, project_id, allocation_percent, role_in_project, start_date, end_date, created_at, updated_at, version)
VALUES
    -- =====================================================
    -- EMP001 (Tuan): Total = 100% → Fully utilized report
    -- =====================================================
    ('a0000001-0000-0000-0000-000000000001',
     'e0000001-0000-0000-0000-000000000001',
     'p0000001-0000-0000-0000-000000000001',
     60, 'Backend Developer', '2025-01-01', '2025-12-31', NOW(), NOW(), 0),

    ('a0000001-0000-0000-0000-000000000002',
     'e0000001-0000-0000-0000-000000000001',
     'p0000001-0000-0000-0000-000000000002',
     40, 'Tech Lead', '2025-03-01', '2025-09-30', NOW(), NOW(), 0),

    -- =====================================================
    -- EMP002 (Nam): Total = 95% → Overloaded report (>90%)
    -- =====================================================
    ('a0000001-0000-0000-0000-000000000003',
     'e0000001-0000-0000-0000-000000000002',
     'p0000001-0000-0000-0000-000000000001',
     50, 'Tech Lead', '2025-01-01', '2025-12-31', NOW(), NOW(), 0),

    ('a0000001-0000-0000-0000-000000000004',
     'e0000001-0000-0000-0000-000000000002',
     'p0000001-0000-0000-0000-000000000003',
     45, 'Architect', '2025-06-01', '2025-12-31', NOW(), NOW(), 0),

    -- =====================================================
    -- EMP003 (Linh): Total = 80% → Available report (20% free)
    -- =====================================================
    ('a0000001-0000-0000-0000-000000000005',
     'e0000001-0000-0000-0000-000000000003',
     'p0000001-0000-0000-0000-000000000002',
     50, 'Frontend Developer', '2025-03-01', '2025-09-30', NOW(), NOW(), 0),

    ('a0000001-0000-0000-0000-000000000006',
     'e0000001-0000-0000-0000-000000000003',
     'p0000001-0000-0000-0000-000000000003',
     30, 'UI Developer', '2025-06-01', '2025-12-31', NOW(), NOW(), 0),

    -- =====================================================
    -- EMP004 (Hung): Total = 40% → Available report (60% free)
    -- =====================================================
    ('a0000001-0000-0000-0000-000000000007',
     'e0000001-0000-0000-0000-000000000004',
     'p0000001-0000-0000-0000-000000000001',
     40, 'Backend Developer', '2025-01-01', '2025-12-31', NOW(), NOW(), 0),

    -- =====================================================
    -- EMP005 (Mai): Total = 0% → No allocation records
    -- (Test: employee with no projects, fully available)
    -- =====================================================

    -- =====================================================
    -- EMP006 (Duc): Total = 60% + 40% = 100% → Valid edge case
    -- (Spec example: Project A 60% + Project B 40% = 100%)
    -- =====================================================
    ('a0000001-0000-0000-0000-000000000008',
     'e0000001-0000-0000-0000-000000000006',
     'p0000001-0000-0000-0000-000000000001',
     60, 'Fullstack Developer', '2025-01-01', '2025-12-31', NOW(), NOW(), 0),

    ('a0000001-0000-0000-0000-000000000009',
     'e0000001-0000-0000-0000-000000000006',
     'p0000001-0000-0000-0000-000000000002',
     40, 'Backend Developer', '2025-03-01', '2025-09-30', NOW(), NOW(), 0),

    -- =====================================================
    -- EMP007 (Hoa): Total = 50% → For update test scenario
    -- =====================================================
    ('a0000001-0000-0000-0000-000000000010',
     'e0000001-0000-0000-0000-000000000007',
     'p0000001-0000-0000-0000-000000000004',
     50, 'DevOps Engineer', '2026-01-01', '2026-06-30', NOW(), NOW(), 0);


-- ============================================================
-- TEST CASE SUMMARY
-- ============================================================
--
-- Business Rule 1 (0 < allocation <= 100):
--   ✅ All allocations are between 1-100
--   ✅ Test via API: try allocation=0 or allocation=101 → should reject
--
-- Business Rule 2 (Total allocation per employee <= 100%):
--   ✅ EMP001: 60% + 40% = 100% (valid edge case)
--   ✅ EMP002: 50% + 45% = 95% (valid, overloaded)
--   ✅ EMP006: 60% + 40% = 100% (spec example)
--   ✅ Test via API: add 50% to EMP001 → 150% → should reject
--
-- Business Rule 3 (No allocation to COMPLETED project):
--   ✅ OLDPRJ (COMPLETED) has no allocations
--   ✅ Test via API: allocate EMP005 to OLDPRJ → should reject
--
-- Reporting:
--   ✅ Utilization: EMP001=100%, EMP002=95%, EMP003=80%, EMP004=40%, EMP006=100%
--   ✅ Available: EMP002(5%), EMP003(20%), EMP004(60%), EMP005(100%), EMP007(50%)
--   ✅ Overloaded (>90%): EMP001(100%), EMP002(95%), EMP006(100%)
--   ✅ Workload API: GET /employees/{id}/workload
-- ============================================================
