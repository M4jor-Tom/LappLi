import { IStrand } from 'app/shared/model/strand.model';
import { AssemblyMean } from 'app/shared/model/enumerations/assembly-mean.model';

export interface ICoreAssembly {
  id?: number;
  productionStep?: number;
  assemblyStep?: number;
  assemblyMean?: AssemblyMean;
  strand?: IStrand;
}

export const defaultValue: Readonly<ICoreAssembly> = {};
