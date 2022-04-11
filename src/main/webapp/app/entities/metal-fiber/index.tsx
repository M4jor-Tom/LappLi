import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MetalFiber from './metal-fiber';
import MetalFiberDetail from './metal-fiber-detail';
import MetalFiberUpdate from './metal-fiber-update';
import MetalFiberDeleteDialog from './metal-fiber-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MetalFiberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MetalFiberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MetalFiberDetail} />
      <ErrorBoundaryRoute path={match.url} component={MetalFiber} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MetalFiberDeleteDialog} />
  </>
);

export default Routes;
