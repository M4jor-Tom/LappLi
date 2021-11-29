import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ElementKind from './element-kind';
import ElementKindDetail from './element-kind-detail';
import ElementKindUpdate from './element-kind-update';
import ElementKindDeleteDialog from './element-kind-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ElementKindUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ElementKindUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ElementKindDetail} />
      <ErrorBoundaryRoute path={match.url} component={ElementKind} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ElementKindDeleteDialog} />
  </>
);

export default Routes;
