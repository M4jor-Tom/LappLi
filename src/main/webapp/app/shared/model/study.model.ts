import dayjs from 'dayjs';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { IUserData } from 'app/shared/model/user-data.model';

export interface IStudy {
  id?: number;
  number?: number | null;
  creationInstant?: string;
  strandSupplies?: IStrandSupply[] | null;
  author?: IUserData;
}

export const defaultValue: Readonly<IStudy> = {};
