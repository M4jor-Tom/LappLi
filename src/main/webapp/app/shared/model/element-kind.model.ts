import { ICopper } from 'app/shared/model/copper.model';
import { IMaterial } from 'app/shared/model/material.model';

export interface IElementKind {
  id?: number;
  designation?: string;
  gramPerMeterLinearMass?: number;
  milimeterDiameter?: number;
  milimeterInsulationThickness?: number;
  copper?: ICopper;
  insulationMaterial?: IMaterial;
}

export const defaultValue: Readonly<IElementKind> = {};
