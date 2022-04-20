import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Strip from './strip';
import StripDetail from './strip-detail';
import StripUpdate from './strip-update';
import StripDeleteDialog from './strip-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StripUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StripUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StripDetail} />
      <ErrorBoundaryRoute path={match.url} component={Strip} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StripDeleteDialog} />
  </>
);

export default Routes;
