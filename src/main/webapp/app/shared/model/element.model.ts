import { IElementKind } from 'app/shared/model/element-kind.model';
import { Color } from 'app/shared/model/enumerations/color.model';

export interface IElement {
  id?: number;
  number?: number;
  designationWithColor?: string;
  numberWithDesignationWithColor?: string;
  color?: Color;
  elementKind?: IElementKind | null;
  milimeterDiameter?: number;
}

export const defaultValue: Readonly<IElement> = {};
