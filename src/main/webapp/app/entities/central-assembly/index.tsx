import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CentralAssembly from './central-assembly';
import CentralAssemblyDetail from './central-assembly-detail';
import CentralAssemblyUpdate from './central-assembly-update';
import CentralAssemblyDeleteDialog from './central-assembly-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CentralAssemblyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CentralAssemblyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CentralAssemblyDetail} />
      <ErrorBoundaryRoute path={match.url} component={CentralAssembly} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CentralAssemblyDeleteDialog} />
  </>
);

export default Routes;
