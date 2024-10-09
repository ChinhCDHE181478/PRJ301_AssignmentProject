import { Role } from "./role";

export interface Account{
    username: string,
    employeeId: number,
    role: string[],
    status: string
}