import { Employee } from "./employee";

export interface Worker{
    workerId: number | null,
    scheduleId: number | null,
    employee: Employee,
    quantity: number | null
}