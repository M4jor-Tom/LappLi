import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MaterialMarkingStatistic from './material-marking-statistic';
import MaterialMarkingStatisticDetail from './material-marking-statistic-detail';
import MaterialMarkingStatisticUpdate from './material-marking-statistic-update';
import MaterialMarkingStatisticDeleteDialog from './material-marking-statistic-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MaterialMarkingStatisticUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MaterialMarkingStatisticUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MaterialMarkingStatisticDetail} />
      <ErrorBoundaryRoute path={match.url} component={MaterialMarkingStatistic} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MaterialMarkingStatisticDeleteDialog} />
  </>
);

export default Routes;
