import { IMaterial } from 'app/shared/model/material.model';

export interface IBangle {
  id?: number;
  number?: number;
  designation?: string;
  gramPerMeterLinearMass?: number;
  milimeterDiameter?: number;
  material?: IMaterial;
}

export const defaultValue: Readonly<IBangle> = {};
