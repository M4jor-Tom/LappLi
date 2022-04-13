import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ContinuityWire from './continuity-wire';
import ContinuityWireDetail from './continuity-wire-detail';
import ContinuityWireUpdate from './continuity-wire-update';
import ContinuityWireDeleteDialog from './continuity-wire-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ContinuityWireUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ContinuityWireUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ContinuityWireDetail} />
      <ErrorBoundaryRoute path={match.url} component={ContinuityWire} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ContinuityWireDeleteDialog} />
  </>
);

export default Routes;
