import { IMaterial } from 'app/shared/model/material.model';
import { Color } from 'app/shared/model/enumerations/color.model';

export interface ICustomComponent {
  id?: number;
  number?: number | null;
  designation?: string | null;
  gramPerMeterLinearMass?: number;
  milimeterDiameter?: number;
  surfaceColor?: Color;
  surfaceMaterial?: IMaterial;
}

export const defaultValue: Readonly<ICustomComponent> = {};
