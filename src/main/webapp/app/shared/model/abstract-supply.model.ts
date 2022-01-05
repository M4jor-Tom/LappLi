import { ICylindricComponent } from './cylindric-component.model';
import { SupplyKind } from './enumerations/supply-kind.model';
import { IMaterial } from './material.model';
import { IPosition } from './position.model';
import { IStrand } from './strand.model';

export interface IAbstractSupply {
  id?: number;
  supplyKind?: SupplyKind;
  apparitions?: number;
  dividedApparitions?: number;
  surfaceMaterial?: IMaterial;
  cylindricComponent?: ICylindricComponent;
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
