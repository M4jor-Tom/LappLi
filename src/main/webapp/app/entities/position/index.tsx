import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Position from './position';
import PositionDetail from './position-detail';
import PositionUpdate from './position-update';
import PositionDeleteDialog from './position-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PositionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PositionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PositionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Position} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PositionDeleteDialog} />
  </>
);

export default Routes;
