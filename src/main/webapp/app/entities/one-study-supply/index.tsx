import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OneStudySupply from './one-study-supply';
import OneStudySupplyDetail from './one-study-supply-detail';
import OneStudySupplyUpdate from './one-study-supply-update';
import OneStudySupplyDeleteDialog from './one-study-supply-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OneStudySupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OneStudySupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OneStudySupplyDetail} />
      <ErrorBoundaryRoute path={match.url} component={OneStudySupply} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OneStudySupplyDeleteDialog} />
  </>
);

export default Routes;
