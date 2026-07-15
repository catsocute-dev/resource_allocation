// API Response wrapper
export interface ApiResponse<T> {
  success: boolean;
  data: T;
  errorMessage?: {
    errorCode: string;
    message: string;
  };
}

// Pagination
export interface PagingResponse<T> {
  currentPage: number;
  pageSize: number;
  totalPages: number;
  totalElement: number;
  data: T[];
}

export interface PagingParams {
  page?: number;
  size?: number;
  direction?: string;
  field?: string;
}

// Employee
export interface Employee {
  id: string;
  employeeCode: string;
  fullName: string;
  email: string;
  role: string;
  department: string;
}

export interface EmployeeCreateRequest {
  employeeCode: string;
  fullName: string;
  email: string;
  role?: string;
  department?: string;
}

// Project
export type ProjectStatus = 'PLANNING' | 'ACTIVE' | 'COMPLETED';

export interface Project {
  id: string;
  projectCode: string;
  projectName: string;
  customer: string;
  startDate: string | null;
  endDate: string | null;
  status: ProjectStatus;
}

export interface ProjectCreateRequest {
  projectCode: string;
  projectName: string;
  customer?: string;
  startDate?: string;
  endDate?: string;
  status: ProjectStatus;
}

// Allocation
export interface Allocation {
  id: string;
  employeeId: string;
  employeeName: string;
  projectId: string;
  projectName: string;
  allocationPercent: number;
  roleInProject: string;
  startDate: string | null;
  endDate: string | null;
}

export interface AllocationCreateRequest {
  employeeId: string;
  projectId: string;
  allocationPercent: number;
  roleInProject?: string;
  startDate?: string;
  endDate?: string;
}

export interface AllocationUpdateRequest {
  employeeId?: string;
  projectId?: string;
  allocationPercent?: number;
  roleInProject?: string;
  startDate?: string;
  endDate?: string;
}

// Reports
export interface WorkloadResponse {
  employeeId: string;
  employeeName: string;
  totalAllocation: number;
  available: number;
}

export interface UtilizationResponse {
  employeeId: string;
  employeeName: string;
  totalAllocation: number;
}

// Auth
export interface AuthRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  authenticated: boolean;
  token: string;
  roles: string[];
}
