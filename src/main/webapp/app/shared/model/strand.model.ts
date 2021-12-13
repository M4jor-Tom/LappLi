import { IISupply } from 'app/shared/model/i-supply.model';
import { OperationType } from 'app/shared/model/enumerations/operation-type.model';

export interface IStrand {
  id?: number;
  designation?: string;
  housingOperationType?: OperationType | null;
  supplies?: IISupply[] | null;
}

export const defaultValue: Readonly<IStrand> = {};
