import { IUser } from 'app/shared/model/user.model';
import { IStudy } from 'app/shared/model/study.model';

export interface IUserData {
  id?: number;
  user?: IUser;
  studies?: IStudy[] | null;
}

export const defaultValue: Readonly<IUserData> = {};
