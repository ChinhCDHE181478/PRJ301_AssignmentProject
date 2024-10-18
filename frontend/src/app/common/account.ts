import { Role } from "./role";

export interface Account{
    username: string,
    employeeId: number,
    role: Set<String>,
    status: string
}