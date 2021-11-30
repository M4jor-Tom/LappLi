import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Bangle from './bangle';
import BangleDetail from './bangle-detail';
import BangleUpdate from './bangle-update';
import BangleDeleteDialog from './bangle-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BangleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BangleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BangleDetail} />
      <ErrorBoundaryRoute path={match.url} component={Bangle} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BangleDeleteDialog} />
  </>
);

export default Routes;
