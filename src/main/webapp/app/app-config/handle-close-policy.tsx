import { RouteComponentProps } from 'react-router-dom';

export const handleClosePolicy = (props: RouteComponentProps) => {
  props.history.goBack();
};
