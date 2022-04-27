export interface ICarrierPlaitFiber {
  id?: number;
  number?: number | null;
  designation?: string | null;
  squareMilimeterSection?: number;
  decaNewtonLoad?: number;
}

export const defaultValue: Readonly<ICarrierPlaitFiber> = {};
