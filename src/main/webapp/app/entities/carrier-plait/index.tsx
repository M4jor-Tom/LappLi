import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CarrierPlait from './carrier-plait';
import CarrierPlaitDetail from './carrier-plait-detail';
import CarrierPlaitUpdate from './carrier-plait-update';
import CarrierPlaitDeleteDialog from './carrier-plait-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CarrierPlaitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CarrierPlaitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CarrierPlaitDetail} />
      <ErrorBoundaryRoute path={match.url} component={CarrierPlait} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CarrierPlaitDeleteDialog} />
  </>
);

export default Routes;
