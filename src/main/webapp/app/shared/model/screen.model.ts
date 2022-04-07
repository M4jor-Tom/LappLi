import { ICopperFiber } from 'app/shared/model/copper-fiber.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { IAbstractOperation } from './abstract-operation.model';
import { AssemblyMean } from './enumerations/assembly-mean.model';
import { IMeanedAssemblableOperation } from './meaned-assemblable-operation.model';

export interface IScreen extends IMeanedAssemblableOperation {
  id?: number;
  assemblyMeanIsSameThanAssemblys?: boolean;
  forcedDiameterAssemblyStep?: number | null;
  copperFiber?: ICopperFiber;
}

export const defaultValue: Readonly<IScreen> = {
  assemblyMeanIsSameThanAssemblys: false,
};
