export interface IAbstractSupply {
  id?: number;
  apparitions?: number;
  description?: string | null;
  meterQuantity?: number;
  hourPreparationTime?: number;
  hourExecutionTime?: number;
  meterPerHourSpeed?: number;

  milimeterDiameter?: number;
  gramPerMeterLinearMass?: number;
}

export const defaultValue: Readonly<IAbstractSupply> = {};
