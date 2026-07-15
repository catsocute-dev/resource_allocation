import { useState } from 'react';
import { useCreateEmployeeMutation } from './employeesApi';
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
import { toast } from 'sonner';
import { Loader2 } from 'lucide-react';

interface EmployeeFormDialogProps {
  open: boolean;
  onOpenChange: (open: boolean) => void;
}

export default function EmployeeFormDialog({
  open,
  onOpenChange,
}: EmployeeFormDialogProps) {
  const [form, setForm] = useState({
    employeeCode: '',
    fullName: '',
    email: '',
    role: '',
    department: '',
  });
  const [createEmployee, { isLoading }] = useCreateEmployeeMutation();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await createEmployee({
        employeeCode: form.employeeCode,
        fullName: form.fullName,
        email: form.email,
        role: form.role || undefined,
        department: form.department || undefined,
      }).unwrap();
      toast.success('Employee created successfully');
      setForm({
        employeeCode: '',
        fullName: '',
        email: '',
        role: '',
        department: '',
      });
      onOpenChange(false);
    } catch {
      toast.error('Failed to create employee');
    }
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>Add Employee</DialogTitle>
          <DialogDescription>
            Create a new employee record. Click save when done.
          </DialogDescription>
        </DialogHeader>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="employeeCode">Employee Code *</Label>
            <Input
              id="employeeCode"
              name="employeeCode"
              placeholder="e.g. EMP001"
              maxLength={20}
              value={form.employeeCode}
              onChange={handleChange}
              required
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="fullName">Full Name *</Label>
            <Input
              id="fullName"
              name="fullName"
              placeholder="e.g. Tuan Ho Anh"
              maxLength={100}
              value={form.fullName}
              onChange={handleChange}
              required
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="email">Email *</Label>
            <Input
              id="email"
              name="email"
              type="email"
              placeholder="e.g. tuanha@company.com"
              maxLength={100}
              value={form.email}
              onChange={handleChange}
              required
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="role">Role</Label>
            <Input
              id="role"
              name="role"
              placeholder="e.g. Senior Developer"
              maxLength={50}
              value={form.role}
              onChange={handleChange}
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="department">Department</Label>
            <Input
              id="department"
              name="department"
              placeholder="e.g. FSOFT-Q1"
              maxLength={50}
              value={form.department}
              onChange={handleChange}
            />
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
                  Creating...
                </>
              ) : (
                'Create Employee'
              )}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}
