import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ContinuityWireLongitLaying from './continuity-wire-longit-laying';
import ContinuityWireLongitLayingDetail from './continuity-wire-longit-laying-detail';
import ContinuityWireLongitLayingUpdate from './continuity-wire-longit-laying-update';
import ContinuityWireLongitLayingDeleteDialog from './continuity-wire-longit-laying-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ContinuityWireLongitLayingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ContinuityWireLongitLayingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ContinuityWireLongitLayingDetail} />
      <ErrorBoundaryRoute path={match.url} component={ContinuityWireLongitLaying} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ContinuityWireLongitLayingDeleteDialog} />
  </>
);

export default Routes;
