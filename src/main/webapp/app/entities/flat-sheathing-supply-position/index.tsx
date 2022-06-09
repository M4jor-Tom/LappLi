import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FlatSheathingSupplyPosition from './flat-sheathing-supply-position';
import FlatSheathingSupplyPositionDetail from './flat-sheathing-supply-position-detail';
import FlatSheathingSupplyPositionUpdate from './flat-sheathing-supply-position-update';
import FlatSheathingSupplyPositionDeleteDialog from './flat-sheathing-supply-position-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FlatSheathingSupplyPositionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FlatSheathingSupplyPositionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FlatSheathingSupplyPositionDetail} />
      <ErrorBoundaryRoute path={match.url} component={FlatSheathingSupplyPosition} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FlatSheathingSupplyPositionDeleteDialog} />
  </>
);

export default Routes;
