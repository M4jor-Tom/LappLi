import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Lifter from './lifter';
import LifterDetail from './lifter-detail';
import LifterUpdate from './lifter-update';
import LifterDeleteDialog from './lifter-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LifterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LifterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LifterDetail} />
      <ErrorBoundaryRoute path={match.url} component={Lifter} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LifterDeleteDialog} />
  </>
);

export default Routes;
