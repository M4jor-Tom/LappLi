import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MyNewComponentSupply from './my-new-component-supply';
import MyNewComponentSupplyDetail from './my-new-component-supply-detail';
import MyNewComponentSupplyUpdate from './my-new-component-supply-update';
import MyNewComponentSupplyDeleteDialog from './my-new-component-supply-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MyNewComponentSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MyNewComponentSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MyNewComponentSupplyDetail} />
      <ErrorBoundaryRoute path={match.url} component={MyNewComponentSupply} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MyNewComponentSupplyDeleteDialog} />
  </>
);

export default Routes;
