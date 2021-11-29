import { IElement } from 'app/shared/model/element.model';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';

export interface IElementSupply {
  id?: number;
  apparitions?: number;
  forcedMarking?: string | null;
  markingType?: MarkingType | null;
  quantity?: number;
  element?: IElement | null;
  bestLiftersNames?: string;
}

export const defaultValue: Readonly<IElementSupply> = {};
