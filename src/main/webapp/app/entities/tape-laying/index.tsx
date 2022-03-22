import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TapeLaying from './tape-laying';
import TapeLayingDetail from './tape-laying-detail';
import TapeLayingUpdate from './tape-laying-update';
import TapeLayingDeleteDialog from './tape-laying-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TapeLayingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TapeLayingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TapeLayingDetail} />
      <ErrorBoundaryRoute path={match.url} component={TapeLaying} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TapeLayingDeleteDialog} />
  </>
);

export default Routes;
