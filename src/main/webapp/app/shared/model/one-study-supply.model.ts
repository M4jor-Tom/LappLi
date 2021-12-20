import { IMaterial } from 'app/shared/model/material.model';
import { IStrand } from 'app/shared/model/strand.model';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { Color } from 'app/shared/model/enumerations/color.model';
import { IAbstractMarkedLiftedSupply } from './abstract-marked-lifted-supply.model';

export interface IOneStudySupply extends IAbstractMarkedLiftedSupply {
  number?: number | null;
  designation?: string | null;
  gramPerMeterLinearMass?: number;
  milimeterDiameter?: number;
  surfaceColor?: Color;
  surfaceMaterial?: IMaterial;
}

export const defaultValue: Readonly<IOneStudySupply> = {};
