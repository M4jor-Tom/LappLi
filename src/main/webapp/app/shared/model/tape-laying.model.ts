import { ITape } from 'app/shared/model/tape.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { AssemblyMean } from 'app/shared/model/enumerations/assembly-mean.model';

export interface ITapeLaying {
  id?: number;
  operationLayer?: number;
  assemblyMean?: AssemblyMean;
  tape?: ITape;
  ownerStrandSupply?: IStrandSupply;
}

export const defaultValue: Readonly<ITapeLaying> = {};
