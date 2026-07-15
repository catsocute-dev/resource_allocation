import { useParams, Link } from 'react-router-dom';
import { useGetEmployeeByIdQuery } from './employeesApi';
import { useGetEmployeeWorkloadQuery } from '@/features/reports/reportsApi';
import { Button } from '@/components/ui/button';
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { ArrowLeft, Loader2 } from 'lucide-react';

export default function EmployeeDetailPage() {
  const { id } = useParams<{ id: string }>();
  const {
    data: employee,
    isLoading,
    error,
  } = useGetEmployeeByIdQuery(id!, { skip: !id });
  const { data: workload, isLoading: workloadLoading } =
    useGetEmployeeWorkloadQuery(id!, { skip: !id });

  if (isLoading) {
    return (
      <div className="flex justify-center py-8">
        <Loader2 className="h-8 w-8 animate-spin text-muted-foreground" />
      </div>
    );
  }

  if (error || !employee) {
    return (
      <div className="space-y-4">
        <Button variant="ghost" render={<Link to="/employees" />}>
          <ArrowLeft className="mr-2 h-4 w-4" />
          Back to Employees
        </Button>
        <Card>
          <CardContent className="py-8 text-center text-muted-foreground">
            Employee not found
          </CardContent>
        </Card>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <Button variant="ghost" render={<Link to="/employees" />}>
        <ArrowLeft className="mr-2 h-4 w-4" />
        Back to Employees
      </Button>

      <div className="grid gap-6 md:grid-cols-2">
        <Card>
          <CardHeader>
            <CardTitle>Employee Information</CardTitle>
            <CardDescription>{employee.employeeCode}</CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="grid grid-cols-2 gap-4">
              <div>
                <p className="text-sm text-muted-foreground">Full Name</p>
                <p className="font-medium">{employee.fullName}</p>
              </div>
              <div>
                <p className="text-sm text-muted-foreground">Email</p>
                <p className="font-medium">{employee.email}</p>
              </div>
              <div>
                <p className="text-sm text-muted-foreground">Role</p>
                <p className="font-medium">{employee.role || '-'}</p>
              </div>
              <div>
                <p className="text-sm text-muted-foreground">Department</p>
                <p className="font-medium">{employee.department || '-'}</p>
              </div>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Workload</CardTitle>
            <CardDescription>Current allocation summary</CardDescription>
          </CardHeader>
          <CardContent>
            {workloadLoading ? (
              <div className="flex justify-center py-4">
                <Loader2 className="h-6 w-6 animate-spin text-muted-foreground" />
              </div>
            ) : workload ? (
              <div className="space-y-4">
                <div>
                  <p className="text-sm text-muted-foreground">
                    Total Allocation
                  </p>
                  <div className="flex items-center gap-2">
                    <p className="text-3xl font-bold">
                      {workload.totalAllocation}%
                    </p>
                    <Badge
                      variant={
                        workload.totalAllocation > 90
                          ? 'destructive'
                          : workload.totalAllocation >= 70
                            ? 'default'
                            : 'secondary'
                      }
                    >
                      {workload.totalAllocation > 90
                        ? 'Overloaded'
                        : workload.totalAllocation >= 70
                          ? 'High'
                          : 'Normal'}
                    </Badge>
                  </div>
                </div>
                <div>
                  <p className="text-sm text-muted-foreground">Available</p>
                  <p className="text-3xl font-bold text-green-500">
                    {workload.available}%
                  </p>
                </div>
                <div className="h-2 w-full rounded-full bg-secondary">
                  <div
                    className={`h-2 rounded-full ${
                      workload.totalAllocation > 90
                        ? 'bg-destructive'
                        : workload.totalAllocation >= 70
                          ? 'bg-primary'
                          : 'bg-green-500'
                    }`}
                    style={{
                      width: `${Math.min(100, workload.totalAllocation)}%`,
                    }}
                  />
                </div>
              </div>
            ) : (
              <p className="text-muted-foreground">No workload data</p>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
