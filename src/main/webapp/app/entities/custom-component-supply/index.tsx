import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CustomComponentSupply from './custom-component-supply';
import CustomComponentSupplyDetail from './custom-component-supply-detail';
import CustomComponentSupplyUpdate from './custom-component-supply-update';
import CustomComponentSupplyDeleteDialog from './custom-component-supply-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CustomComponentSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CustomComponentSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CustomComponentSupplyDetail} />
      <ErrorBoundaryRoute path={match.url} component={CustomComponentSupply} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CustomComponentSupplyDeleteDialog} />
  </>
);

export default Routes;
