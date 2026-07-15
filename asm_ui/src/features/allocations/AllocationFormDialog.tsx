import { useState, useEffect } from 'react';
import { useGetEmployeesQuery } from '@/features/employees/employeesApi';
import { useGetProjectsQuery } from '@/features/projects/projectsApi';
import {
  useCreateAllocationMutation,
  useUpdateAllocationMutation,
} from './allocationsApi';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { toast } from 'sonner';
import { Loader2 } from 'lucide-react';
import type { Allocation } from '@/types';

interface AllocationFormDialogProps {
  open: boolean;
  onOpenChange: (open: boolean) => void;
  editData?: Allocation | null;
}

export default function AllocationFormDialog({
  open,
  onOpenChange,
  editData,
}: AllocationFormDialogProps) {
  const [form, setForm] = useState({
    employeeId: '',
    projectId: '',
    allocationPercent: 50,
    roleInProject: '',
    startDate: '',
    endDate: '',
  });

  const { data: employeesData } = useGetEmployeesQuery({ page: 1, size: 100 });
  const { data: projectsData } = useGetProjectsQuery({ page: 1, size: 100 });
  const [createAllocation, { isLoading: isCreating }] =
    useCreateAllocationMutation();
  const [updateAllocation, { isLoading: isUpdating }] =
    useUpdateAllocationMutation();

  const employees = employeesData?.data ?? [];
  const projects = projectsData?.data ?? [];
  const isLoading = isCreating || isUpdating;

  useEffect(() => {
    if (editData) {
      setForm({
        employeeId: editData.employeeId,
        projectId: editData.projectId,
        allocationPercent: editData.allocationPercent,
        roleInProject: editData.roleInProject,
        startDate: editData.startDate || '',
        endDate: editData.endDate || '',
      });
    } else {
      setForm({
        employeeId: '',
        projectId: '',
        allocationPercent: 50,
        roleInProject: '',
        startDate: '',
        endDate: '',
      });
    }
  }, [editData, open]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (form.allocationPercent < 1 || form.allocationPercent > 100) {
      toast.error('Allocation must be between 1% and 100%');
      return;
    }

    try {
      if (editData) {
        await updateAllocation({
          id: editData.id,
          body: {
            employeeId: form.employeeId,
            projectId: form.projectId,
            allocationPercent: form.allocationPercent,
            roleInProject: form.roleInProject || undefined,
            startDate: form.startDate || undefined,
            endDate: form.endDate || undefined,
          },
        }).unwrap();
        toast.success('Allocation updated successfully');
      } else {
        await createAllocation({
          employeeId: form.employeeId,
          projectId: form.projectId,
          allocationPercent: form.allocationPercent,
          roleInProject: form.roleInProject || undefined,
          startDate: form.startDate || undefined,
          endDate: form.endDate || undefined,
        }).unwrap();
        toast.success('Allocation created successfully');
      }
      onOpenChange(false);
    } catch (err: unknown) {
      const error = err as { data?: { errorMessage?: { message?: string } } };
      toast.error(
        error?.data?.errorMessage?.message ||
          `Failed to ${editData ? 'update' : 'create'} allocation`,
      );
    }
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>
            {editData ? 'Edit Allocation' : 'Add Allocation'}
          </DialogTitle>
          <DialogDescription>
            {editData
              ? 'Update the allocation details.'
              : 'Allocate an employee to a project.'}
          </DialogDescription>
        </DialogHeader>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="employeeId">Employee *</Label>
            <Select
              value={form.employeeId}
              onValueChange={(value) =>
                setForm((prev) => ({ ...prev, employeeId: value ?? '' }))
              }
            >
              <SelectTrigger>
                <SelectValue placeholder="Select employee" />
              </SelectTrigger>
              <SelectContent>
                {employees.map((emp) => (
                  <SelectItem key={emp.id} value={emp.id}>
                    {emp.employeeCode} - {emp.fullName}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
          <div className="space-y-2">
            <Label htmlFor="projectId">Project *</Label>
            <Select
              value={form.projectId}
              onValueChange={(value) =>
                setForm((prev) => ({ ...prev, projectId: value ?? '' }))
              }
            >
              <SelectTrigger>
                <SelectValue placeholder="Select project" />
              </SelectTrigger>
              <SelectContent>
                {projects
                  .filter((p) => p.status !== 'COMPLETED')
                  .map((proj) => (
                    <SelectItem key={proj.id} value={proj.id}>
                      {proj.projectCode} - {proj.projectName}
                    </SelectItem>
                  ))}
              </SelectContent>
            </Select>
          </div>
          <div className="space-y-2">
            <Label htmlFor="allocationPercent">
              Allocation Percent * (1-100)
            </Label>
            <div className="flex items-center gap-4">
              <Input
                id="allocationPercent"
                type="range"
                min={1}
                max={100}
                value={form.allocationPercent}
                onChange={(e) =>
                  setForm((prev) => ({
                    ...prev,
                    allocationPercent: parseInt(e.target.value),
                  }))
                }
                className="flex-1"
              />
              <span className="w-12 text-right font-bold">
                {form.allocationPercent}%
              </span>
            </div>
          </div>
          <div className="space-y-2">
            <Label htmlFor="roleInProject">Role In Project</Label>
            <Input
              id="roleInProject"
              placeholder="e.g. Backend Developer"
              maxLength={100}
              value={form.roleInProject}
              onChange={(e) =>
                setForm((prev) => ({
                  ...prev,
                  roleInProject: e.target.value,
                }))
              }
            />
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-2">
              <Label htmlFor="startDate">Start Date</Label>
              <Input
                id="startDate"
                type="date"
                value={form.startDate}
                onChange={(e) =>
                  setForm((prev) => ({
                    ...prev,
                    startDate: e.target.value,
                  }))
                }
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="endDate">End Date</Label>
              <Input
                id="endDate"
                type="date"
                value={form.endDate}
                onChange={(e) =>
                  setForm((prev) => ({
                    ...prev,
                    endDate: e.target.value,
                  }))
                }
              />
            </div>
          </div>
          <DialogFooter>
            <Button
              type="button"
              variant="outline"
              onClick={() => onOpenChange(false)}
            >
              Cancel
            </Button>
            <Button type="submit" disabled={isLoading}>
              {isLoading ? (
                <>
                  <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                  {editData ? 'Updating...' : 'Creating...'}
                </>
              ) : editData ? (
                'Update Allocation'
              ) : (
                'Create Allocation'
              )}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}
