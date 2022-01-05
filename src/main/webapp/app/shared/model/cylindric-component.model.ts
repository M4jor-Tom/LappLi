import { IDesignable } from './designable.model';

export interface ICylindricComponent extends IDesignable {
  milimeterDiameter?: number;
  gramPerMeterLinearMass?: number;

  squareMilimeterSurface?: number;
}

export const defaultValue: Readonly<ICylindricComponent> = {};
