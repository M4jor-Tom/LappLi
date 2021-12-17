import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Strand from './strand';
import StrandDetail from './strand-detail';
import StrandUpdate from './strand-update';
import StrandDeleteDialog from './strand-delete-dialog';
import StrandSubSupply from './strand-sub-supply';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StrandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StrandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StrandDetail} />

      <ErrorBoundaryRoute exact path={`${match.url}/:id/supply`} component={StrandSubSupply} />

      <ErrorBoundaryRoute path={match.url} component={Strand} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StrandDeleteDialog} />
  </>
);

export default Routes;