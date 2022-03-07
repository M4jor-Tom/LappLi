import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Tape from './tape';
import TapeDetail from './tape-detail';
import TapeUpdate from './tape-update';
import TapeDeleteDialog from './tape-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TapeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TapeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TapeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Tape} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TapeDeleteDialog} />
  </>
);

export default Routes;
