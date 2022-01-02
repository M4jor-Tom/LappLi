export interface ICylindricComponent {
  milimeterDiameter?: number;
  gramPerMeterLinearMass?: number;

  squareMilimeterSurface?: number;
}

export const defaultValue: Readonly<ICylindricComponent> = {};
