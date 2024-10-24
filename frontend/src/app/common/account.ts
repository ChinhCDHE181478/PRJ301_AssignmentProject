import { Role } from "./role";

export interface Account{
    userId: number | null,
    username: string | null,
    employeeId: number | null,
    role: Role[],
    status: string | null
}