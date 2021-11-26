import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Element from './element';
import ElementDetail from './element-detail';
import ElementUpdate from './element-update';
import ElementDeleteDialog from './element-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ElementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ElementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ElementDetail} />
      <ErrorBoundaryRoute path={match.url} component={Element} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ElementDeleteDialog} />
  </>
);

export default Routes;
