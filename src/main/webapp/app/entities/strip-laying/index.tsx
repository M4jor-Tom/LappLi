import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StripLaying from './strip-laying';
import StripLayingDetail from './strip-laying-detail';
import StripLayingUpdate from './strip-laying-update';
import StripLayingDeleteDialog from './strip-laying-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StripLayingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StripLayingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StripLayingDetail} />
      <ErrorBoundaryRoute path={match.url} component={StripLaying} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StripLayingDeleteDialog} />
  </>
);

export default Routes;
