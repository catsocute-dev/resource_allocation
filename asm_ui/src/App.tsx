import { BrowserRouter, Routes, Route } from 'react-router-dom';
import AppLayout from '@/components/layout/AppLayout';
import LoginPage from '@/features/auth/LoginPage';
import EmployeeListPage from '@/features/employees/EmployeeListPage';
import EmployeeDetailPage from '@/features/employees/EmployeeDetailPage';
import ProjectListPage from '@/features/projects/ProjectListPage';
import ProjectDetailPage from '@/features/projects/ProjectDetailPage';
import AllocationListPage from '@/features/allocations/AllocationListPage';
import ReportsPage from '@/features/reports/ReportsPage';
import { Navigate } from 'react-router-dom';

function Dashboard() {
  return <Navigate to="/employees" replace />;
}

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/" element={<AppLayout />}>
          <Route index element={<Dashboard />} />
          <Route path="employees" element={<EmployeeListPage />} />
          <Route path="employees/:id" element={<EmployeeDetailPage />} />
          <Route path="projects" element={<ProjectListPage />} />
          <Route path="projects/:id" element={<ProjectDetailPage />} />
          <Route path="allocations" element={<AllocationListPage />} />
          <Route path="reports" element={<ReportsPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
