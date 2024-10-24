import { Department } from "./department";

export interface Plan{
    planId: number | null,
    startDate: Date | null,
    endDate: Date | null,
    department: Department | null
}