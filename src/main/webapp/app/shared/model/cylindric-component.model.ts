import { IDesignable } from './designable.model';
import { CylindricComponentKind } from './enumerations/cylindric-component-kind.model';

export interface ICylindricComponent extends IDesignable {
  milimeterDiameter?: number;
  gramPerMeterLinearMass?: number;
  cylindricComponentKind?: CylindricComponentKind;

  squareMilimeterSurface?: number;
}

export const defaultValue: Readonly<ICylindricComponent> = {};
