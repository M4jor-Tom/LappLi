import { ICoreAssembly } from 'app/shared/model/core-assembly.model';
import { IIntersticeAssembly } from 'app/shared/model/interstice-assembly.model';
import { ISheathing } from 'app/shared/model/sheathing.model';
import { IStrand } from 'app/shared/model/strand.model';
import { ICentralAssembly } from 'app/shared/model/central-assembly.model';
import { IStudy } from 'app/shared/model/study.model';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { AssemblyMean } from 'app/shared/model/enumerations/assembly-mean.model';
import { IAbstractNonCentralAssembly } from './abstract-non-central-assembly.model';
import { ITapeLaying } from './tape-laying.model';
import { IAbstractOperation } from './abstract-operation.model';

export interface IStrandSupply {
  id?: number;
  designation?: string;
  apparitions?: number;
  markingType?: MarkingType;
  description?: string | null;
  diameterAssemblyStep?: number;
  assemblyMean?: AssemblyMean;
  hasAssemblies?: boolean;
  isFlat?: boolean;
  isCylindric?: boolean;
  couldBeFlat?: boolean;
  couldBeCylindric?: boolean;
  forceCentralUtilityComponent?: boolean;
  strand?: IStrand;
  centralAssembly?: ICentralAssembly | null;
  nonCentralOperations?: IAbstractOperation[];
  study?: IStudy;
}

export const defaultValue: Readonly<IStrandSupply> = {
  forceCentralUtilityComponent: false,
};
