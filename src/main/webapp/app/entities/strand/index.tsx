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
import OneStudySupplyUpdate from '../one-study-supply/one-study-supply-update';
import OneStudySupplyDeleteDialog from '../one-study-supply/one-study-supply-delete-dialog';
import StrandSubOperation from './strand-sub-operations';
import CentralAssemblyUpdate from '../central-assembly/central-assembly-update';
import CentralAssemblyDeleteDialog from '../central-assembly/central-assembly-delete-dialog';
import CoreAssemblyUpdate from '../core-assembly/core-assembly-update';
import CoreAssemblyDeleteDialog from '../core-assembly/core-assembly-delete-dialog';
import IntersticeAssemblyUpdate from '../interstice-assembly/interstice-assembly-update';
import IntersticeAssemblyDeleteDialog from '../interstice-assembly/interstice-assembly-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StrandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StrandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StrandDetail} />

      {/* <ErrorBoundaryRoute exact path={`${match.url}/:id/supply`} component={StrandSubSupply} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/operation`} component={StrandSubOperation} />

      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/custom-component-supply/new`} component={CustomComponentSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/custom-component-supply/:id/edit`} component={CustomComponentSupplyUpdate} />
      <ErrorBoundaryRoute
        exact
        path={`${match.url}/:strand_id/custom-component-supply/:id/delete`}
        component={CustomComponentSupplyDeleteDialog}
      />

      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/supply/bangle-supply/new`} component={BangleSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/supply/bangle-supply/:id/edit`} component={BangleSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/supply/bangle-supply/:id/delete`} component={BangleSupplyDeleteDialog} />

      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/supply/element-supply/new`} component={ElementSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/supply/element-supply/:id/edit`} component={ElementSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/supply/element-supply/:id/delete`} component={ElementSupplyDeleteDialog} />

      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/supply/one-study-supply/new`} component={OneStudySupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/supply/one-study-supply/:id/edit`} component={OneStudySupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/supply/one-study-supply/:id/delete`} component={OneStudySupplyDeleteDialog} />*/}

      {/* <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/central-assembly/new`} component={CentralAssemblyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/central-assembly/:id/edit`} component={CentralAssemblyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/central-assembly/:id/delete`} component={CentralAssemblyDeleteDialog} />

      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/core-assembly/new`} component={CoreAssemblyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/core-assembly/:id/edit`} component={CoreAssemblyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/core-assembly/:id/delete`} component={CoreAssemblyDeleteDialog} />

      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/interstice-assembly/new`} component={IntersticeAssemblyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:strand_id/interstice-assembly/:id/edit`} component={IntersticeAssemblyUpdate} />
      <ErrorBoundaryRoute
        exact
        path={`${match.url}/:strand_id/interstice-assembly/:id/delete`}
        component={IntersticeAssemblyDeleteDialog}
      />*/}

      <ErrorBoundaryRoute path={match.url} component={Strand} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StrandDeleteDialog} />
  </>
);

export default Routes;
