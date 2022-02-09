import { ICylindricComponent } from './cylindric-component.model';
import { SupplyKind } from './enumerations/supply-kind.model';
import { IMaterial } from './material.model';
import { IStrand } from './strand.model';
import { ISupplyPosition } from './supply-position.model';

export interface IAbstractSupply {
  id?: number;
  supplyPosition?: ISupplyPosition;
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
  ownerStrand?: IStrand;
}

export const defaultValue: Readonly<IAbstractSupply> = {};
