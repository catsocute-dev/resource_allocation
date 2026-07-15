import { useState } from 'react';
import { useGetEmployeesQuery } from '@/features/employees/employeesApi';
import { useGetProjectsQuery } from '@/features/projects/projectsApi';
import { useDeleteAllocationMutation } from './allocationsApi';
import AllocationFormDialog from './AllocationFormDialog';
import { Button } from '@/components/ui/button';
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
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';
import { Badge } from '@/components/ui/badge';
import { Plus, Loader2 } from 'lucide-react';
import { toast } from 'sonner';
import type { Allocation } from '@/types';

export default function AllocationListPage() {
  const [dialogOpen, setDialogOpen] = useState(false);
  const [deleteTarget, setDeleteTarget] = useState<Allocation | null>(null);
  const [editTarget, setEditTarget] = useState<Allocation | null>(null);

  const { data: employeesData } = useGetEmployeesQuery({ page: 1, size: 100 });
  const { data: projectsData } = useGetProjectsQuery({ page: 1, size: 100 });
  const [deleteAllocation, { isLoading: isDeleting }] =
    useDeleteAllocationMutation();

  // Collect all allocations from employees (we need to fetch per-employee workload)
  // For simplicity, we'll show a combined view
  const employees = employeesData?.data ?? [];
  const projects = projectsData?.data ?? [];

  const handleDelete = async () => {
    if (!deleteTarget) return;
    try {
      await deleteAllocation(deleteTarget.id).unwrap();
      toast.success('Allocation deleted successfully');
      setDeleteTarget(null);
    } catch {
      toast.error('Failed to delete allocation');
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold tracking-tight">Allocations</h1>
          <p className="text-muted-foreground">
            Manage resource allocations across projects
          </p>
        </div>
        <Button
          onClick={() => {
            setEditTarget(null);
            setDialogOpen(true);
          }}
        >
          <Plus className="mr-2 h-4 w-4" />
          Add Allocation
        </Button>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Allocation Management</CardTitle>
          <CardDescription>
            Allocate employees to projects with percentage-based time分配
          </CardDescription>
        </CardHeader>
        <CardContent>
          <div className="mb-4 grid grid-cols-2 gap-4 md:grid-cols-3">
            <Card>
              <CardContent className="pt-6">
                <p className="text-2xl font-bold">{employees.length}</p>
                <p className="text-sm text-muted-foreground">Employees</p>
              </CardContent>
            </Card>
            <Card>
              <CardContent className="pt-6">
                <p className="text-2xl font-bold">{projects.length}</p>
                <p className="text-sm text-muted-foreground">Projects</p>
              </CardContent>
            </Card>
            <Card>
              <CardContent className="pt-6">
                <p className="text-2xl font-bold">
                  {projects.filter((p) => p.status === 'ACTIVE').length}
                </p>
                <p className="text-sm text-muted-foreground">Active Projects</p>
              </CardContent>
            </Card>
          </div>

          <p className="mb-4 text-sm text-muted-foreground">
            To manage individual allocations, create a new allocation using the
            button above. Employee allocations can be viewed from the Employee
            detail page.
          </p>

          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Employee</TableHead>
                <TableHead>Project</TableHead>
                <TableHead>Status</TableHead>
                <TableHead className="text-right">Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {employees.length === 0 && projects.length === 0 ? (
                <TableRow>
                  <TableCell
                    colSpan={4}
                    className="text-center text-muted-foreground"
                  >
                    No data. Add employees and projects first.
                  </TableCell>
                </TableRow>
              ) : (
                employees.slice(0, 5).map((emp) => (
                  <TableRow key={emp.id}>
                    <TableCell className="font-medium">
                      {emp.fullName}
                    </TableCell>
                    <TableCell>-</TableCell>
                    <TableCell>
                      <Badge variant="secondary">View Detail</Badge>
                    </TableCell>
                    <TableCell className="text-right">
                      <Button
                        variant="ghost"
                        size="sm"
                        onClick={() => {
                          setEditTarget(null);
                          setDialogOpen(true);
                        }}
                      >
                        <Plus className="mr-1 h-3 w-3" />
                        Allocate
                      </Button>
                    </TableCell>
                  </TableRow>
                ))
              )}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      <AllocationFormDialog
        open={dialogOpen}
        onOpenChange={setDialogOpen}
        editData={editTarget}
      />

      <Dialog
        open={!!deleteTarget}
        onOpenChange={() => setDeleteTarget(null)}
      >
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Delete Allocation</DialogTitle>
            <DialogDescription>
              Are you sure you want to delete this allocation? This action
              cannot be undone.
            </DialogDescription>
          </DialogHeader>
          <DialogFooter>
            <Button
              variant="outline"
              onClick={() => setDeleteTarget(null)}
            >
              Cancel
            </Button>
            <Button
              variant="destructive"
              onClick={handleDelete}
              disabled={isDeleting}
            >
              {isDeleting ? (
                <>
                  <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                  Deleting...
                </>
              ) : (
                'Delete'
              )}
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  );
}
