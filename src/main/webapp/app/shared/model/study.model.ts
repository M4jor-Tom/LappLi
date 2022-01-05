import dayjs from 'dayjs';
import { IStrand } from 'app/shared/model/strand.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { IUserData } from 'app/shared/model/user-data.model';

export interface IStudy {
  id?: number;
  number?: number | null;
  lastEditionInstant?: string;
  strands?: IStrand[] | null;
  strandSupplies?: IStrandSupply[] | null;
  author?: IUserData;
}

export const defaultValue: Readonly<IStudy> = {};
