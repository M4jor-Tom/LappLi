import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Plait from './plait';
import PlaitDetail from './plait-detail';
import PlaitUpdate from './plait-update';
import PlaitDeleteDialog from './plait-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PlaitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PlaitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PlaitDetail} />
      <ErrorBoundaryRoute path={match.url} component={Plait} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PlaitDeleteDialog} />
  </>
);

export default Routes;
