import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SupplyPosition from './supply-position';
import SupplyPositionDetail from './supply-position-detail';
import SupplyPositionUpdate from './supply-position-update';
import SupplyPositionDeleteDialog from './supply-position-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SupplyPositionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SupplyPositionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SupplyPositionDetail} />
      <ErrorBoundaryRoute path={match.url} component={SupplyPosition} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SupplyPositionDeleteDialog} />
  </>
);

export default Routes;
