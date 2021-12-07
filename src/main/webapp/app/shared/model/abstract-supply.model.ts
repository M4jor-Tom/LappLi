export interface IAbstractSupply {
  id?: number;
  apparitions?: number;
  meterQuantity?: number;
  hourPreparationTime?: number;
  hourExecutionTime?: number;
  meterPerSecondSpeed?: number;
  description?: string;

  milimeterDiameter?: number;
  gramPerMeterLinearMass?: number;
}

export const defaultValue: Readonly<IAbstractSupply> = {};
