import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BangleSupply from './bangle-supply';
import BangleSupplyDetail from './bangle-supply-detail';
import BangleSupplyUpdate from './bangle-supply-update';
import BangleSupplyDeleteDialog from './bangle-supply-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BangleSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BangleSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BangleSupplyDetail} />
      <ErrorBoundaryRoute path={match.url} component={BangleSupply} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BangleSupplyDeleteDialog} />
  </>
);

export default Routes;
