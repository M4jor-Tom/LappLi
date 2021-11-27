import { ILifter } from 'app/shared/model/lifter.model';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';

export interface ILifterRunMeasure {
  id?: number;
  milimeterDiameter?: number | null;
  markingType?: MarkingType | null;
  lifter?: ILifter | null;
}

export const defaultValue: Readonly<ILifterRunMeasure> = {};
