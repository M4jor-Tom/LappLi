import { ICopperFiber } from 'app/shared/model/copper-fiber.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { IAbstractOperation } from './abstract-operation.model';
import { AssemblyMean } from './enumerations/assembly-mean.model';
import { MetalFiberKind } from './enumerations/metal-fiber-kind.model';
import { IMeanedAssemblableOperation } from './meaned-assemblable-operation.model';

export interface IScreen extends IMeanedAssemblableOperation {
  id?: number;
  assemblyMeanIsSameThanAssemblys?: boolean;
  forcedDiameterAssemblyStep?: number | null;
  anonymousCopperFiberNumber?: number | null;
  anonymousCopperFiberDesignation?: string | null;
  anonymousCopperFiberKind?: MetalFiberKind | null;
  anonymousCopperFiberMilimeterDiameter?: number | null;
  copperFiber?: ICopperFiber | null;
}

export const defaultValue: Readonly<IScreen> = {
  assemblyMeanIsSameThanAssemblys: false,
};
