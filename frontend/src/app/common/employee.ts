import { Department } from './department';

export interface Employee{
    employeeId: number | null,
    employeeName: string | null,
    department: Department | null
}