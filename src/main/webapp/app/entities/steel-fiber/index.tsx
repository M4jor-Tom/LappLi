import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SteelFiber from './steel-fiber';
import SteelFiberDetail from './steel-fiber-detail';
import SteelFiberUpdate from './steel-fiber-update';
import SteelFiberDeleteDialog from './steel-fiber-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SteelFiberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SteelFiberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SteelFiberDetail} />
      <ErrorBoundaryRoute path={match.url} component={SteelFiber} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SteelFiberDeleteDialog} />
  </>
);

export default Routes;
