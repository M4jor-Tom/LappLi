import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import IntersticeAssembly from './interstice-assembly';
import IntersticeAssemblyDetail from './interstice-assembly-detail';
import IntersticeAssemblyUpdate from './interstice-assembly-update';
import IntersticeAssemblyDeleteDialog from './interstice-assembly-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IntersticeAssemblyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IntersticeAssemblyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IntersticeAssemblyDetail} />
      <ErrorBoundaryRoute path={match.url} component={IntersticeAssembly} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={IntersticeAssemblyDeleteDialog} />
  </>
);

export default Routes;
