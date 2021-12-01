import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ElementKindEdition from './element-kind-edition';
import ElementKindEditionDetail from './element-kind-edition-detail';
import ElementKindEditionUpdate from './element-kind-edition-update';
import ElementKindEditionDeleteDialog from './element-kind-edition-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ElementKindEditionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ElementKindEditionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ElementKindEditionDetail} />
      <ErrorBoundaryRoute path={match.url} component={ElementKindEdition} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ElementKindEditionDeleteDialog} />
  </>
);

export default Routes;
