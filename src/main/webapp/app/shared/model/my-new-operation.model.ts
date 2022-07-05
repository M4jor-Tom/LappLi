import { IMyNewComponent } from 'app/shared/model/my-new-component.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';

export interface IMyNewOperation {
  id?: number;
  operationLayer?: number;
  operationData?: number;
  anonymousMyNewComponentNumber?: number | null;
  anonymousMyNewComponentDesignation?: string | null;
  anonymousMyNewComponentData?: number | null;
  myNewComponent?: IMyNewComponent | null;
  ownerStrandSupply?: IStrandSupply;
}

export const defaultValue: Readonly<IMyNewOperation> = {};
