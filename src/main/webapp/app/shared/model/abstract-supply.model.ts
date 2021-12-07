export interface IAbstractSupply {
  id?: number;
  apparitions?: number;
  description?: string | null;
  meterQuantity?: number;
  hourPreparationTime?: number;
  hourExecutionTime?: number;
  meterPerSecondSpeed?: number;

  milimeterDiameter?: number;
  gramPerMeterLinearMass?: number;
}

export const defaultValue: Readonly<IAbstractSupply> = {};
