import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LifterRunMeasure from './lifter-run-measure';
import LifterRunMeasureDetail from './lifter-run-measure-detail';
import LifterRunMeasureUpdate from './lifter-run-measure-update';
import LifterRunMeasureDeleteDialog from './lifter-run-measure-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LifterRunMeasureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LifterRunMeasureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LifterRunMeasureDetail} />
      <ErrorBoundaryRoute path={match.url} component={LifterRunMeasure} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LifterRunMeasureDeleteDialog} />
  </>
);

export default Routes;
