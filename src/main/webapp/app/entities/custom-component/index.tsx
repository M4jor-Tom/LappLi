import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CustomComponent from './custom-component';
import CustomComponentDetail from './custom-component-detail';
import CustomComponentUpdate from './custom-component-update';
import CustomComponentDeleteDialog from './custom-component-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CustomComponentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CustomComponentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CustomComponentDetail} />
      <ErrorBoundaryRoute path={match.url} component={CustomComponent} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CustomComponentDeleteDialog} />
  </>
);

export default Routes;
