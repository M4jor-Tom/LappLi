import { IMaterial } from 'app/shared/model/material.model';

export interface ICustomComponent {
  id?: number;
  number?: number | null;
  designation?: string | null;
  gramPerMeterLinearMass?: number | null;
  milimeterDiameter?: number | null;
  surfaceMaterial?: IMaterial;
}

export const defaultValue: Readonly<ICustomComponent> = {};
