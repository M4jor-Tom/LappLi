import { IElement } from 'app/shared/model/element.model';
import { IStrand } from 'app/shared/model/strand.model';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';

export interface IElementSupply {
  id?: number;
  apparitions?: number;
  markingType?: MarkingType;
  description?: string | null;
  element?: IElement;
  strand?: IStrand;
}

export const defaultValue: Readonly<IElementSupply> = {};
