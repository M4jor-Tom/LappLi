import { ITape } from 'app/shared/model/tape.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { AssemblyMean } from 'app/shared/model/enumerations/assembly-mean.model';
import { IAbstractOperation } from './abstract-operation.model';

export interface ITapeLaying extends IAbstractOperation {
  assemblyMean?: AssemblyMean;
  tape?: ITape;
}

export const defaultValue: Readonly<ITapeLaying> = {};
