export interface ICarrierPlaitFiber {
  id?: number;
  number?: number | null;
  designation?: string | null;
  decitexTitration?: number;
  gramPerSquareMilimeterPerMeterDensity?: number;
  decaNewtonLoad?: number;
}

export const defaultValue: Readonly<ICarrierPlaitFiber> = {};
