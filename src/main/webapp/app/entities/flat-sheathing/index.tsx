import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FlatSheathing from './flat-sheathing';
import FlatSheathingDetail from './flat-sheathing-detail';
import FlatSheathingUpdate from './flat-sheathing-update';
import FlatSheathingDeleteDialog from './flat-sheathing-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FlatSheathingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FlatSheathingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FlatSheathingDetail} />
      <ErrorBoundaryRoute path={match.url} component={FlatSheathing} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FlatSheathingDeleteDialog} />
  </>
);

export default Routes;
