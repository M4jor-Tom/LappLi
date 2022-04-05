import { ICopperFiber } from 'app/shared/model/copper-fiber.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';

export interface IScreen {
  id?: number;
  operationLayer?: number;
  assemblyMeanIsSameThanAssemblys?: boolean;
  forcedDiameterAssemblyStep?: number | null;
  copperFiber?: ICopperFiber;
  ownerStrandSupply?: IStrandSupply;
}

export const defaultValue: Readonly<IScreen> = {
  assemblyMeanIsSameThanAssemblys: false,
};
