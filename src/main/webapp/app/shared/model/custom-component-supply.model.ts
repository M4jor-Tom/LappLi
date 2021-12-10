import { ICustomComponent } from 'app/shared/model/custom-component.model';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';

export interface ICustomComponentSupply {
  id?: number;
  apparitions?: number;
  description?: string | null;
  markingType?: MarkingType;
  customComponent?: ICustomComponent;
}

export const defaultValue: Readonly<ICustomComponentSupply> = {};
