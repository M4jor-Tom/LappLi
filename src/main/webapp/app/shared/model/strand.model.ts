import { ICoreAssembly } from 'app/shared/model/core-assembly.model';
import { IIntersticeAssembly } from 'app/shared/model/interstice-assembly.model';
import { IElementSupply } from 'app/shared/model/element-supply.model';
import { IBangleSupply } from 'app/shared/model/bangle-supply.model';
import { ICustomComponentSupply } from 'app/shared/model/custom-component-supply.model';
import { IOneStudySupply } from 'app/shared/model/one-study-supply.model';
import { ICentralAssembly } from 'app/shared/model/central-assembly.model';
import { IStudy } from 'app/shared/model/study.model';

export interface IStrand {
  id?: number;
  designation?: string;
  coreAssemblies?: ICoreAssembly[] | null;
  intersticeAssemblies?: IIntersticeAssembly[] | null;
  elementSupplies?: IElementSupply[] | null;
  bangleSupplies?: IBangleSupply[] | null;
  customComponentSupplies?: ICustomComponentSupply[] | null;
  oneStudySupplies?: IOneStudySupply[] | null;
  centralAssembly?: ICentralAssembly | null;
  suppliesCount?: number;
  futureStudy?: IStudy;
}

export const defaultValue: Readonly<IStrand> = {};
