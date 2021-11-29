import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ElementSupply from './element-supply';
import ElementSupplyDetail from './element-supply-detail';
import ElementSupplyUpdate from './element-supply-update';
import ElementSupplyDeleteDialog from './element-supply-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ElementSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ElementSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ElementSupplyDetail} />
      <ErrorBoundaryRoute path={match.url} component={ElementSupply} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ElementSupplyDeleteDialog} />
  </>
);

export default Routes;
