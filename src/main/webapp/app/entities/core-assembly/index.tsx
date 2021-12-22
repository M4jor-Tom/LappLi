import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CoreAssembly from './core-assembly';
import CoreAssemblyDetail from './core-assembly-detail';
import CoreAssemblyUpdate from './core-assembly-update';
import CoreAssemblyDeleteDialog from './core-assembly-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CoreAssemblyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CoreAssemblyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CoreAssemblyDetail} />
      <ErrorBoundaryRoute path={match.url} component={CoreAssembly} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CoreAssemblyDeleteDialog} />
  </>
);

export default Routes;
