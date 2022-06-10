import { IElementKind } from 'app/shared/model/element-kind.model';
import { Color } from 'app/shared/model/enumerations/color.model';
import { ICylindricComponent } from './cylindric-component.model';

export interface IElement extends ICylindricComponent {
  id?: number;
  number?: number;
  designationWithColor?: string;
  numberWithDesignationWithColor?: string;
  color?: Color;
  elementKind?: IElementKind;
}

export const defaultValue: Readonly<IElement> = {};
