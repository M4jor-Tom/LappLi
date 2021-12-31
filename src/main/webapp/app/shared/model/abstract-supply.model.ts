import { IPosition } from './position.model';
import { IStrand } from './strand.model';

export interface IAbstractSupply {
  id?: number;
  apparitions?: number;
  dividedApparitions?: number;
  designation?: string | null;
  description?: string | null;
  meterQuantity?: number;
  formatedHourPreparationTime?: number;
  formatedHourExecutionTime?: number;
  meterPerHourSpeed?: number;
  strand?: IStrand;
  position?: IPosition;
}

export const defaultValue: Readonly<IAbstractSupply> = {};
