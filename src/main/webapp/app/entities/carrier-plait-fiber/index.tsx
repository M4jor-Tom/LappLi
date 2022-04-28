import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CarrierPlaitFiber from './carrier-plait-fiber';
import CarrierPlaitFiberDetail from './carrier-plait-fiber-detail';
import CarrierPlaitFiberUpdate from './carrier-plait-fiber-update';
import CarrierPlaitFiberDeleteDialog from './carrier-plait-fiber-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CarrierPlaitFiberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CarrierPlaitFiberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CarrierPlaitFiberDetail} />
      <ErrorBoundaryRoute path={match.url} component={CarrierPlaitFiber} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CarrierPlaitFiberDeleteDialog} />
  </>
);

export default Routes;
