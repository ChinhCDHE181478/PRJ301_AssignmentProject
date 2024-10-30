import { Role } from "./role";

export interface AccountResponse{
    userId: number | null,
    username: string | null,
    employeeId: number | null,
    role: Role | null,
    status: string | null
}