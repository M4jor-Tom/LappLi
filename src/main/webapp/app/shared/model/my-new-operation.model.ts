import { IMyNewComponent } from 'app/shared/model/my-new-component.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { IAbstractOperation } from './abstract-operation.model';

export interface IMyNewOperation extends IAbstractOperation {
  operationData?: number;
  anonymousMyNewComponentNumber?: number | null;
  anonymousMyNewComponentDesignation?: string | null;
  anonymousMyNewComponentData?: number | null;
  myNewComponent?: IMyNewComponent | null;
  finalMyNewComponent?: IMyNewComponent | null;
}

export const defaultValue: Readonly<IMyNewOperation> = {};
