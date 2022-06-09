import { ISupplyPosition } from 'app/shared/model/supply-position.model';
import { IFlatSheathing } from 'app/shared/model/flat-sheathing.model';

export interface IFlatSheathingSupplyPosition {
  id?: number;
  locationInOwnerFlatSheathing?: number;
  supplyPosition?: ISupplyPosition;
  ownerFlatSheathing?: IFlatSheathing;
}

export const defaultValue: Readonly<IFlatSheathingSupplyPosition> = {};
