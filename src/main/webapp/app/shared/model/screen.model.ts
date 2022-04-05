import { ICopperFiber } from 'app/shared/model/copper-fiber.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { IAbstractOperation } from './abstract-operation.model';
import { AssemblyMean } from './enumerations/assembly-mean.model';

export interface IScreen extends IAbstractOperation {
  id?: number;
  assemblyMeanIsSameThanAssemblys?: boolean;
  assemblyMean?: AssemblyMean;
  diameterAssemblyStep?: number;
  forcedDiameterAssemblyStep?: number | null;
  copperFiber?: ICopperFiber;
}

export const defaultValue: Readonly<IScreen> = {
  assemblyMeanIsSameThanAssemblys: false,
};
