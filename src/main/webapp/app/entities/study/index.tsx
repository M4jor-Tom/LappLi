import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Study from './study';
import StudyDetail from './study-detail';
import StudyUpdate from './study-update';
import StudyDeleteDialog from './study-delete-dialog';
import StudyStrandSupply from './study-strand-supply';
import StrandSupplyUpdate from '../strand-supply/strand-supply-update';
import StrandSupplyDeleteDialog from '../strand-supply/strand-supply-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StudyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StudyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StudyDetail} />

      <ErrorBoundaryRoute exact path={`${match.url}/:study_id/study-supplies`} component={StudyStrandSupply} />
      <ErrorBoundaryRoute exact path={`${match.url}/:study_id/study-supplies/new`} component={StrandSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:study_id/study-supplies/:id/edit`} component={StrandSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:study_id/study-supplies/:id/delete`} component={StrandSupplyDeleteDialog} />

      <ErrorBoundaryRoute path={match.url} component={Study} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StudyDeleteDialog} />
  </>
);

export default Routes;
