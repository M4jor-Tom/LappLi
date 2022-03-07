import { ITape } from 'app/shared/model/tape.model';
import { AssemblyMean } from 'app/shared/model/enumerations/assembly-mean.model';

export interface ITapeLaying {
  id?: number;
  operationLayer?: number;
  assemblyMean?: AssemblyMean;
  tape?: ITape;
}

export const defaultValue: Readonly<ITapeLaying> = {};
