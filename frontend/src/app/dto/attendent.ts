import { Worker } from "./worker";

export interface Attendent{
    attendentId: number| null,
    worker: Worker,
    quantity: number | null,
    alpha: number | null
}