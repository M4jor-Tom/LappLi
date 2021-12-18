import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Strand from './strand';
import StrandDetail from './strand-detail';
import StrandUpdate from './strand-update';
import StrandDeleteDialog from './strand-delete-dialog';
import StrandSubSupply from './strand-sub-supply';
import CustomComponentSupplyUpdate from '../custom-component-supply/custom-component-supply-update';
import CustomComponentSupplyDeleteDialog from '../custom-component-supply/custom-component-supply-delete-dialog';
import BangleSupplyUpdate from '../bangle-supply/bangle-supply-update';
import BangleSupplyDeleteDialog from '../bangle-supply/bangle-supply-delete-dialog';
import ElementSupplyUpdate from '../element-supply/element-supply-update';
import ElementSupplyDeleteDialog from '../element-supply/element-supply-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StrandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StrandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StrandDetail} />

      <ErrorBoundaryRoute exact path={`${match.url}/:id/supply`} component={StrandSubSupply} />

      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/custom-component-supply/new`} component={CustomComponentSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/custom-component-supply/:id/edit`} component={CustomComponentSupplyUpdate} />
      <ErrorBoundaryRoute
        exact
        path={`${match.url}/:strand_id/custom-component-supply/:id/delete`}
        component={CustomComponentSupplyDeleteDialog}
      />

      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/bangle-supply/new`} component={BangleSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/bangle-supply/:id/edit`} component={BangleSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/bangle-supply/:id/delete`} component={BangleSupplyDeleteDialog} />

      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/element-supply/new`} component={ElementSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/element-supply/:id/edit`} component={ElementSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/element-supply/:id/delete`} component={ElementSupplyDeleteDialog} />

      <ErrorBoundaryRoute path={match.url} component={Strand} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StrandDeleteDialog} />
  </>
);

export default Routes;
