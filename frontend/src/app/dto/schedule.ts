import { Shift } from "./shift";

export interface Schedule{
    scheduleId: number | null,
    campaignId: number | null,
    date: Date | null,
    quantity: number | null,
    shift: Shift | null
}