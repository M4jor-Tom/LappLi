import { IMaterial } from 'app/shared/model/material.model';
import { IStrand } from 'app/shared/model/strand.model';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { Color } from 'app/shared/model/enumerations/color.model';

export interface IOneStudySupply {
  id?: number;
  apparitions?: number | null;
  number?: number | null;
  designation?: string | null;
  description?: string | null;
  markingType?: MarkingType;
  gramPerMeterLinearMass?: number;
  milimeterDiameter?: number;
  surfaceColor?: Color;
  surfaceMaterial?: IMaterial | null;
  strand?: IStrand;
}

export const defaultValue: Readonly<IOneStudySupply> = {};
