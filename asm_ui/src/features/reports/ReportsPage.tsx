import {
  useGetUtilizationReportQuery,
  useGetAvailableResourcesQuery,
  useGetOverloadedEmployeesQuery,
} from './reportsApi';
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { Badge } from '@/components/ui/badge';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { Loader2, Users, UserCheck, AlertTriangle } from 'lucide-react';

function getAllocationColor(percent: number) {
  if (percent > 90) return 'text-red-500';
  if (percent >= 70) return 'text-yellow-500';
  return 'text-green-500';
}

function getAllocationBadge(percent: number) {
  if (percent > 90) return <Badge variant="destructive">Overloaded</Badge>;
  if (percent >= 70) return <Badge variant="default">High</Badge>;
  return <Badge variant="secondary">Normal</Badge>;
}

export default function ReportsPage() {
  const {
    data: utilization,
    isLoading: utilizationLoading,
  } = useGetUtilizationReportQuery();
  const {
    data: available,
    isLoading: availableLoading,
  } = useGetAvailableResourcesQuery();
  const {
    data: overloaded,
    isLoading: overloadedLoading,
  } = useGetOverloadedEmployeesQuery();

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold tracking-tight">Reports</h1>
        <p className="text-muted-foreground">
          Employee utilization and resource availability reports
        </p>
      </div>

      <Tabs defaultValue="utilization" className="space-y-4">
        <TabsList>
          <TabsTrigger value="utilization" className="flex items-center gap-2">
            <Users className="h-4 w-4" />
            Utilization
          </TabsTrigger>
          <TabsTrigger value="available" className="flex items-center gap-2">
            <UserCheck className="h-4 w-4" />
            Available
          </TabsTrigger>
          <TabsTrigger value="overloaded" className="flex items-center gap-2">
            <AlertTriangle className="h-4 w-4" />
            Overloaded
          </TabsTrigger>
        </TabsList>

        <TabsContent value="utilization">
          <Card>
            <CardHeader>
              <CardTitle>Employee Utilization Report</CardTitle>
              <CardDescription>
                Total allocation percentage for each employee
              </CardDescription>
            </CardHeader>
            <CardContent>
              {utilizationLoading ? (
                <div className="flex justify-center py-8">
                  <Loader2 className="h-8 w-8 animate-spin text-muted-foreground" />
                </div>
              ) : (
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Employee ID</TableHead>
                      <TableHead>Name</TableHead>
                      <TableHead className="text-right">
                        Total Allocation
                      </TableHead>
                      <TableHead>Status</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {!utilization || utilization.length === 0 ? (
                      <TableRow>
                        <TableCell
                          colSpan={4}
                          className="text-center text-muted-foreground"
                        >
                          No utilization data available
                        </TableCell>
                      </TableRow>
                    ) : (
                      utilization.map((emp) => (
                        <TableRow key={emp.employeeId}>
                          <TableCell className="font-medium">
                            {emp.employeeId}
                          </TableCell>
                          <TableCell>{emp.employeeName}</TableCell>
                          <TableCell className="text-right">
                            <span
                              className={`font-bold ${getAllocationColor(emp.totalAllocation)}`}
                            >
                              {emp.totalAllocation}%
                            </span>
                          </TableCell>
                          <TableCell>
                            {getAllocationBadge(emp.totalAllocation)}
                          </TableCell>
                        </TableRow>
                      ))
                    )}
                  </TableBody>
                </Table>
              )}
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="available">
          <Card>
            <CardHeader>
              <CardTitle>Available Resources</CardTitle>
              <CardDescription>
                Employees with available capacity (allocation &lt; 100%)
              </CardDescription>
            </CardHeader>
            <CardContent>
              {availableLoading ? (
                <div className="flex justify-center py-8">
                  <Loader2 className="h-8 w-8 animate-spin text-muted-foreground" />
                </div>
              ) : (
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Employee ID</TableHead>
                      <TableHead>Name</TableHead>
                      <TableHead className="text-right">
                        Total Allocation
                      </TableHead>
                      <TableHead className="text-right">Available</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {!available || available.length === 0 ? (
                      <TableRow>
                        <TableCell
                          colSpan={4}
                          className="text-center text-muted-foreground"
                        >
                          No available resources
                        </TableCell>
                      </TableRow>
                    ) : (
                      available.map((emp) => (
                        <TableRow key={emp.employeeId}>
                          <TableCell className="font-medium">
                            {emp.employeeId}
                          </TableCell>
                          <TableCell>{emp.employeeName}</TableCell>
                          <TableCell className="text-right">
                            {emp.totalAllocation}%
                          </TableCell>
                          <TableCell className="text-right">
                            <span className="font-bold text-green-500">
                              {100 - emp.totalAllocation}%
                            </span>
                          </TableCell>
                        </TableRow>
                      ))
                    )}
                  </TableBody>
                </Table>
              )}
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="overloaded">
          <Card>
            <CardHeader>
              <CardTitle>Overloaded Employees</CardTitle>
              <CardDescription>
                Employees with high workload (allocation &gt; 90%)
              </CardDescription>
            </CardHeader>
            <CardContent>
              {overloadedLoading ? (
                <div className="flex justify-center py-8">
                  <Loader2 className="h-8 w-8 animate-spin text-muted-foreground" />
                </div>
              ) : (
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Employee ID</TableHead>
                      <TableHead>Name</TableHead>
                      <TableHead className="text-right">
                        Total Allocation
                      </TableHead>
                      <TableHead>Status</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {!overloaded || overloaded.length === 0 ? (
                      <TableRow>
                        <TableCell
                          colSpan={4}
                          className="text-center text-muted-foreground"
                        >
                          No overloaded employees
                        </TableCell>
                      </TableRow>
                    ) : (
                      overloaded.map((emp) => (
                        <TableRow key={emp.employeeId}>
                          <TableCell className="font-medium">
                            {emp.employeeId}
                          </TableCell>
                          <TableCell>{emp.employeeName}</TableCell>
                          <TableCell className="text-right">
                            <span className="font-bold text-red-500">
                              {emp.totalAllocation}%
                            </span>
                          </TableCell>
                          <TableCell>
                            <Badge variant="destructive">Overloaded</Badge>
                          </TableCell>
                        </TableRow>
                      ))
                    )}
                  </TableBody>
                </Table>
              )}
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  );
}
