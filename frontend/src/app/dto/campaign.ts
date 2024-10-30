import { Product } from "./product";

export interface Campaign{
    campaignId: number | null,
    planId: number | null,
    product: Product | null,
    quantity: number | null,
    unitEffortDays: number | null
}