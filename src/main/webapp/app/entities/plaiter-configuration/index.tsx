import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PlaiterConfiguration from './plaiter-configuration';
import PlaiterConfigurationDetail from './plaiter-configuration-detail';
import PlaiterConfigurationUpdate from './plaiter-configuration-update';
import PlaiterConfigurationDeleteDialog from './plaiter-configuration-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PlaiterConfigurationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PlaiterConfigurationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PlaiterConfigurationDetail} />
      <ErrorBoundaryRoute path={match.url} component={PlaiterConfiguration} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PlaiterConfigurationDeleteDialog} />
  </>
);

export default Routes;
