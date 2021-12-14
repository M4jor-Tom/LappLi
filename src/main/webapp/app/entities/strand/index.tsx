import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from '../../../app/shared/error/error-boundary-route';

import Strand from './strand';
import StrandDetail from './strand-detail';
import StrandUpdate from './strand-update';
import StrandDeleteDialog from './strand-delete-dialog';
import StrandBangleSupplyUpdate from './supply/strand-bangle-supply-update';
import StrandCustomComponentSupplyUpdate from './supply/strand-custom-component-supply-update';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StrandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StrandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StrandDetail} />
      <ErrorBoundaryRoute exact path={`${match.url}/bangle-supply/:bangle_supply_id/edit`} component={StrandBangleSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/bangle-supply/:strand_id/new`} component={StrandBangleSupplyUpdate} />
      <ErrorBoundaryRoute
        exact
        path={`${match.url}/custom-component-supply/:custom_component_supply_id/edit`}
        component={StrandCustomComponentSupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url}/custom-component-supply/:strand_id/new`}
        component={StrandCustomComponentSupplyUpdate}
      />
      <ErrorBoundaryRoute path={match.url} component={Strand} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StrandDeleteDialog} />
  </>
);

export default Routes;
