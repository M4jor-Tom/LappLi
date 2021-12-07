import { IMaterial } from 'app/shared/model/material.model';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { MarkingTechnique } from 'app/shared/model/enumerations/marking-technique.model';

export interface IMaterialMarkingStatistic {
  id?: number;
  markingType?: MarkingType;
  markingTechnique?: MarkingTechnique;
  meterPerSecondSpeed?: number;
  materials?: IMaterial[];
}

export const defaultValue: Readonly<IMaterialMarkingStatistic> = {};
