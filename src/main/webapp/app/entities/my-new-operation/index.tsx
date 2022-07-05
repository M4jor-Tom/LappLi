import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MyNewOperation from './my-new-operation';
import MyNewOperationDetail from './my-new-operation-detail';
import MyNewOperationUpdate from './my-new-operation-update';
import MyNewOperationDeleteDialog from './my-new-operation-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MyNewOperationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MyNewOperationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MyNewOperationDetail} />
      <ErrorBoundaryRoute path={match.url} component={MyNewOperation} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MyNewOperationDeleteDialog} />
  </>
);

export default Routes;
