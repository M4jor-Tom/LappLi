import { SupplyState } from './enumerations/supply-state.model';
import { IPosition } from './position.model';
import { IStrand } from './strand.model';

export interface IAbstractSupply {
  id?: number;
  supplyState?: SupplyState;
  apparitions?: number;
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
