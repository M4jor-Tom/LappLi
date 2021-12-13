import { IStrand } from './strand.model';

export interface IAbstractSupply {
  id?: number;
  apparitions?: number;
  description?: string | null;
  meterQuantity?: number;
  formatedHourPreparationTime?: number;
  formatedHourExecutionTime?: number;
  meterPerHourSpeed?: number;
  strand?: IStrand;
}

export const defaultValue: Readonly<IAbstractSupply> = {};
