import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MyNewComponent from './my-new-component';
import MyNewComponentDetail from './my-new-component-detail';
import MyNewComponentUpdate from './my-new-component-update';
import MyNewComponentDeleteDialog from './my-new-component-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MyNewComponentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MyNewComponentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MyNewComponentDetail} />
      <ErrorBoundaryRoute path={match.url} component={MyNewComponent} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MyNewComponentDeleteDialog} />
  </>
);

export default Routes;
