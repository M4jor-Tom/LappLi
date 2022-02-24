import { ICoreAssembly } from 'app/shared/model/core-assembly.model';
import { IIntersticeAssembly } from 'app/shared/model/interstice-assembly.model';
import { ISheathing } from 'app/shared/model/sheathing.model';
import { IElementSupply } from 'app/shared/model/element-supply.model';
import { IBangleSupply } from 'app/shared/model/bangle-supply.model';
import { ICustomComponentSupply } from 'app/shared/model/custom-component-supply.model';
import { IOneStudySupply } from 'app/shared/model/one-study-supply.model';
import { ICentralAssembly } from 'app/shared/model/central-assembly.model';
import { IStudy } from 'app/shared/model/study.model';
import { IAbstractOperation } from './abstract-operation.model';
import { ISupplyPosition } from './supply-position.model';
import { AssemblyMean } from './enumerations/assembly-mean.model';

export interface IStrand {
  id?: number;
  designation?: string;
  diameterAssemblyStep?: number;
  assemblyMean?: AssemblyMean;
  operations?: IAbstractOperation[] | null;
  nonAssemblyOperations?: IAbstractOperation[] | null;
  coreAssemblies?: ICoreAssembly[] | null;
  intersticeAssemblies?: IIntersticeAssembly[] | null;
  sheathings?: ISheathing[] | null;
  supplyPositions?: ISupplyPosition[] | null;
  elementSupplies?: IElementSupply[] | null;
  bangleSupplies?: IBangleSupply[] | null;
  customComponentSupplies?: ICustomComponentSupply[] | null;
  oneStudySupplies?: IOneStudySupply[] | null;
  centralAssembly?: ICentralAssembly | null;
  suppliesCountsCommonDividers?: number[];
  undividedSuppliedComponentsCount?: number;
  futureStudy?: IStudy;
}

export const defaultValue: Readonly<IStrand> = {};
