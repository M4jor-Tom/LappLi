import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Material from './material';
import MaterialDetail from './material-detail';
import MaterialUpdate from './material-update';
import MaterialDeleteDialog from './material-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MaterialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MaterialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MaterialDetail} />
      <ErrorBoundaryRoute path={match.url} component={Material} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MaterialDeleteDialog} />
  </>
);

export default Routes;
