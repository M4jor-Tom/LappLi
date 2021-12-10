import { IMaterial } from 'app/shared/model/material.model';

export interface ICustomComponent {
  id?: number;
  number?: number | null;
  designation?: string | null;
  gramPerMeterLinearMass?: number;
  milimeterDiameter?: number;
  surfaceMaterial?: IMaterial;
}

export const defaultValue: Readonly<ICustomComponent> = {};
