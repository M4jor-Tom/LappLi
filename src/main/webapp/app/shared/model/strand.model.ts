import { ICentralAssembly } from 'app/shared/model/central-assembly.model';
import { ICoreAssembly } from 'app/shared/model/core-assembly.model';
import { IIntersticeAssembly } from 'app/shared/model/interstice-assembly.model';
import { IElementSupply } from 'app/shared/model/element-supply.model';
import { IBangleSupply } from 'app/shared/model/bangle-supply.model';
import { ICustomComponentSupply } from 'app/shared/model/custom-component-supply.model';
import { IOneStudySupply } from 'app/shared/model/one-study-supply.model';

export interface IStrand {
  id?: number;
  designation?: string;
  centralAssembly?: ICentralAssembly | null;
  coreAssemblies?: ICoreAssembly[] | null;
  intersticialAssemblies?: IIntersticeAssembly[] | null;
  elementSupplies?: IElementSupply[] | null;
  bangleSupplies?: IBangleSupply[] | null;
  customComponentSupplies?: ICustomComponentSupply[] | null;
  oneStudySupplies?: IOneStudySupply[] | null;
}

export const defaultValue: Readonly<IStrand> = {};
