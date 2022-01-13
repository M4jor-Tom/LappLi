import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Sheathing from './sheathing';
import SheathingDetail from './sheathing-detail';
import SheathingUpdate from './sheathing-update';
import SheathingDeleteDialog from './sheathing-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SheathingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SheathingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SheathingDetail} />
      <ErrorBoundaryRoute path={match.url} component={Sheathing} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SheathingDeleteDialog} />
  </>
);

export default Routes;
