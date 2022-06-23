import { ISupplyPosition } from 'app/shared/model/supply-position.model';
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
import { IAbstractSupply } from './abstract-supply.model';

export interface IStrandSupply extends IAbstractSupply {
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
  ownerSupplyPositions?: ISupplyPosition[];
  strand?: IStrand;
  centralAssembly?: ICentralAssembly | null;
  nonCentralOperations?: IAbstractOperation[];
  study?: IStudy;
}

export function isStrandSupply(object: IAbstractOperation): object is IStrandSupply {
  return (
    Object.prototype.hasOwnProperty.call(object, 'designation') ||
    Object.prototype.hasOwnProperty.call(object, 'apparitions') ||
    Object.prototype.hasOwnProperty.call(object, 'markingType') ||
    Object.prototype.hasOwnProperty.call(object, 'description') ||
    Object.prototype.hasOwnProperty.call(object, 'diameterAssemblyStep') ||
    Object.prototype.hasOwnProperty.call(object, 'assemblyMean') ||
    Object.prototype.hasOwnProperty.call(object, 'hasAssemblies') ||
    Object.prototype.hasOwnProperty.call(object, 'isFlat') ||
    Object.prototype.hasOwnProperty.call(object, 'isCylindric') ||
    Object.prototype.hasOwnProperty.call(object, 'couldBeFlat') ||
    Object.prototype.hasOwnProperty.call(object, 'couldBeCylindric') ||
    Object.prototype.hasOwnProperty.call(object, 'forceCentralUtilityComponent') ||
    Object.prototype.hasOwnProperty.call(object, 'ownerSupplyPositions') ||
    Object.prototype.hasOwnProperty.call(object, 'strand') ||
    Object.prototype.hasOwnProperty.call(object, 'centralAssembly') ||
    Object.prototype.hasOwnProperty.call(object, 'nonCentralOperations') ||
    Object.prototype.hasOwnProperty.call(object, 'study')
  );
}

export const defaultValue: Readonly<IStrandSupply> = {
  forceCentralUtilityComponent: false,
};
