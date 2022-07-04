import { ISupplyPosition } from 'app/shared/model/supply-position.model';
import { IMyNewComponent } from 'app/shared/model/my-new-component.model';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { IAbstractSupply } from './abstract-supply.model';

export interface IMyNewComponentSupply extends IAbstractSupply {
  id?: number;
  markingType?: MarkingType;
  ownerSupplyPositions?: ISupplyPosition[];
  myNewComponent?: IMyNewComponent;
}

export const defaultValue: Readonly<IMyNewComponentSupply> = {};
