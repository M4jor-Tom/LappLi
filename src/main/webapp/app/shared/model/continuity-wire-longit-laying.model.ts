import { IContinuityWire } from 'app/shared/model/continuity-wire.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { MetalFiberKind } from 'app/shared/model/enumerations/metal-fiber-kind.model';
import { Flexibility } from 'app/shared/model/enumerations/flexibility.model';

export interface IContinuityWireLongitLaying {
  id?: number;
  operationLayer?: number;
  anonymousContinuityWireDesignation?: string | null;
  anonymousContinuityWireGramPerMeterLinearMass?: number | null;
  anonymousContinuityWireMetalFiberKind?: MetalFiberKind | null;
  anonymousContinuityWireMilimeterDiameter?: number | null;
  anonymousContinuityWireFlexibility?: Flexibility | null;
  continuityWire?: IContinuityWire | null;
  ownerStrandSupply?: IStrandSupply;
}

export const defaultValue: Readonly<IContinuityWireLongitLaying> = {};
