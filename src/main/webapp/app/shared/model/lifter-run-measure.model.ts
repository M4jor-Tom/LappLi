import { ILifter } from 'app/shared/model/lifter.model';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { MarkingTechnique } from 'app/shared/model/enumerations/marking-technique.model';

export interface ILifterRunMeasure {
  id?: number;
  milimeterDiameter?: number | null;
  meterPerSecondSpeed?: number | null;
  markingType?: MarkingType;
  markingTechnique?: MarkingTechnique;
  formatedHourPreparationTime?: number | null;
  lifter?: ILifter;
}

export const defaultValue: Readonly<ILifterRunMeasure> = {};
