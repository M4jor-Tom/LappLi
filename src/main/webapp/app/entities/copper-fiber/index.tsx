import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CopperFiber from './copper-fiber';
import CopperFiberDetail from './copper-fiber-detail';
import CopperFiberUpdate from './copper-fiber-update';
import CopperFiberDeleteDialog from './copper-fiber-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CopperFiberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CopperFiberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CopperFiberDetail} />
      <ErrorBoundaryRoute path={match.url} component={CopperFiber} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CopperFiberDeleteDialog} />
  </>
);

export default Routes;
