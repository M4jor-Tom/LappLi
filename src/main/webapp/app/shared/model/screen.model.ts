import { ICopperFiber } from 'app/shared/model/copper-fiber.model';

export interface IScreen {
  id?: number;
  operationLayer?: number;
  assemblyMeanIsSameThanAssemblys?: boolean;
  forcedDiameterAssemblyStep?: number | null;
  copperFiber?: ICopperFiber;
}

export const defaultValue: Readonly<IScreen> = {
  assemblyMeanIsSameThanAssemblys: false,
};
