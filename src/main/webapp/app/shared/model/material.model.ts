import { IMaterialMarkingStatistic } from 'app/shared/model/material-marking-statistic.model';

export interface IMaterial {
  id?: number;
  number?: number;
  designation?: string;
  materialMarkingStatisticList?: IMaterialMarkingStatistic | null;
}

export const defaultValue: Readonly<IMaterial> = {};
