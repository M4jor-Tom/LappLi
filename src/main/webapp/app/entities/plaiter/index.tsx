import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Plaiter from './plaiter';
import PlaiterDetail from './plaiter-detail';
import PlaiterUpdate from './plaiter-update';
import PlaiterDeleteDialog from './plaiter-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PlaiterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PlaiterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PlaiterDetail} />
      <ErrorBoundaryRoute path={match.url} component={Plaiter} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PlaiterDeleteDialog} />
  </>
);

export default Routes;
