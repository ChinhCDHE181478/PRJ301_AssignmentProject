import { Role } from "./role";

export interface AccountRequest{
    userId: number | null,
    username: string | null,
    password: string | null,
    employeeId: number | null,
    role: Role[],
    status: string | null
}