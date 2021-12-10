import { ICustomComponent } from 'app/shared/model/custom-component.model';

export interface ICustomComponentSupply {
  id?: number;
  apparitions?: number;
  description?: string | null;
  customComponent?: ICustomComponent;
}

export const defaultValue: Readonly<ICustomComponentSupply> = {};
