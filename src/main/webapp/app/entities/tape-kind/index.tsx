import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TapeKind from './tape-kind';
import TapeKindDetail from './tape-kind-detail';
import TapeKindUpdate from './tape-kind-update';
import TapeKindDeleteDialog from './tape-kind-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TapeKindUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TapeKindUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TapeKindDetail} />
      <ErrorBoundaryRoute path={match.url} component={TapeKind} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TapeKindDeleteDialog} />
  </>
);

export default Routes;
