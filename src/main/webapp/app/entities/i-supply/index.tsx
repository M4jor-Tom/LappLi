import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ISupply from './i-supply';
import ISupplyDetail from './i-supply-detail';
import ISupplyUpdate from './i-supply-update';
import ISupplyDeleteDialog from './i-supply-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ISupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ISupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ISupplyDetail} />
      <ErrorBoundaryRoute path={match.url} component={ISupply} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ISupplyDeleteDialog} />
  </>
);

export default Routes;
