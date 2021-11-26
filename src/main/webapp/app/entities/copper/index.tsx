import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Copper from './copper';
import CopperDetail from './copper-detail';
import CopperUpdate from './copper-update';
import CopperDeleteDialog from './copper-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CopperUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CopperUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CopperDetail} />
      <ErrorBoundaryRoute path={match.url} component={Copper} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CopperDeleteDialog} />
  </>
);

export default Routes;
