import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Screen from './screen';
import ScreenDetail from './screen-detail';
import ScreenUpdate from './screen-update';
import ScreenDeleteDialog from './screen-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ScreenUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ScreenUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ScreenDetail} />
      <ErrorBoundaryRoute path={match.url} component={Screen} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ScreenDeleteDialog} />
  </>
);

export default Routes;
