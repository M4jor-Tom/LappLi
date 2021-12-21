import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Study from './study';
import StudyDetail from './study-detail';
import StudyUpdate from './study-update';
import StudyDeleteDialog from './study-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StudyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StudyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StudyDetail} />
      <ErrorBoundaryRoute path={match.url} component={Study} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StudyDeleteDialog} />
  </>
);

export default Routes;
