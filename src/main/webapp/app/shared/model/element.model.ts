import { IElementKind } from 'app/shared/model/element-kind.model';
import { Color } from 'app/shared/model/enumerations/color.model';

export interface IElement {
  id?: number;
  number?: number;
  color?: Color;
  elementKind?: IElementKind | null;
}

export const defaultValue: Readonly<IElement> = {};
