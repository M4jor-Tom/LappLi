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
import StrandUpdate from '../strand/strand-update';
import StrandSupplySubOperation from '../strand-supply/strand-supply-sub-operations';
import CustomComponentSupplyUpdate from '../custom-component-supply/custom-component-supply-update';
import CustomComponentSupplyDeleteDialog from '../custom-component-supply/custom-component-supply-delete-dialog';
import BangleSupplyUpdate from '../bangle-supply/bangle-supply-update';
import BangleSupplyDeleteDialog from '../bangle-supply/bangle-supply-delete-dialog';
import ElementSupplyUpdate from '../element-supply/element-supply-update';
import ElementSupplyDeleteDialog from '../element-supply/element-supply-delete-dialog';
import OneStudySupplyUpdate from '../one-study-supply/one-study-supply-update';
import OneStudySupplyDeleteDialog from '../one-study-supply/one-study-supply-delete-dialog';
import CentralAssemblyUpdate from '../central-assembly/central-assembly-update';
import CentralAssemblyDeleteDialog from '../central-assembly/central-assembly-delete-dialog';
import CoreAssemblyUpdate from '../core-assembly/core-assembly-update';
import CoreAssemblyDeleteDialog from '../core-assembly/core-assembly-delete-dialog';
import IntersticeAssemblyUpdate from '../interstice-assembly/interstice-assembly-update';
import IntersticeAssemblyDeleteDialog from '../interstice-assembly/interstice-assembly-delete-dialog';
import StrandSubSupply from '../strand/strand-sub-supply';

const strandSupplyZoneUrlPefixToStrandSupplyId = '/:study_id/study-supplies/strand-supplies';

const strandZoneUrlPefixToStrandId = '/:study_id/study-supplies/strand';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StudyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StudyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StudyDetail} />

      {/* (1): Strand creation */}
      {/* <ErrorBoundaryRoute exact path={`${match.url}/:study_id/strand/new`} component={StrandUpdate} />*/}

      {/* (2): Strand Supply creation */}
      <ErrorBoundaryRoute exact path={`${match.url}/:study_id/study-supplies/new`} component={StrandSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:study_id/study-supplies/:id/edit`} component={StrandSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:study_id/study-supplies/:id/delete`} component={StrandSupplyDeleteDialog} />

      {/* (3): Creating directly a Strand in a StrandSupply sub menu */}
      <ErrorBoundaryRoute exact path={`${match.url}/:study_id/study-supplies`} component={StudyStrandSupply} />
      {/* <ErrorBoundaryRoute exact path={`${match.url}/:study_id/study-supplies/strand/new`} component={StrandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:study_id/study-supplies/:strand_supply_id/strand/new`} component={StrandUpdate} />*/}

      {/* STRAND SUPPLY ZONE */}

      {/* (4): Creating directly a Strand in a StrandSupply sub menu */}
      <ErrorBoundaryRoute exact path={`${match.url}/:study_id/study-supplies/strand/new`} component={StrandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:study_id/study-supplies/:strand_supply_id/strand/new`} component={StrandUpdate} />

      {/* (5): Strand's sub-supply and sub-operations observing */}
      {/* STRAND'S SUB SUPPLY ZONE */}
      <ErrorBoundaryRoute exact path={`${match.url + strandZoneUrlPefixToStrandId}/:id/supply`} component={StrandSubSupply} />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:id/operation`}
        component={StrandSupplySubOperation}
      />

      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/supply/custom-component-supply/new`}
        component={CustomComponentSupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/supply/custom-component-supply/:id/edit`}
        component={CustomComponentSupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/supply/custom-component-supply/:id/delete`}
        component={CustomComponentSupplyDeleteDialog}
      />

      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/supply/bangle-supply/new`}
        component={BangleSupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/supply/bangle-supply/:id/edit`}
        component={BangleSupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/supply/bangle-supply/:id/delete`}
        component={BangleSupplyDeleteDialog}
      />

      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/supply/element-supply/new`}
        component={ElementSupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/supply/element-supply/:id/edit`}
        component={ElementSupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/supply/element-supply/:id/delete`}
        component={ElementSupplyDeleteDialog}
      />

      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/supply/one-study-supply/new`}
        component={OneStudySupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/supply/one-study-supply/:id/edit`}
        component={OneStudySupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/supply/one-study-supply/:id/delete`}
        component={OneStudySupplyDeleteDialog}
      />

      {/* STRAND'S SUB OPERATION ZONE */}
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/operation/central-assembly/new`}
        component={CentralAssemblyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/operation/central-assembly/:id/edit`}
        component={CentralAssemblyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/operation/central-assembly/:id/delete`}
        component={CentralAssemblyDeleteDialog}
      />

      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/operation/core-assembly/new`}
        component={CoreAssemblyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/operation/core-assembly/:id/edit`}
        component={CoreAssemblyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/operation/core-assembly/:id/delete`}
        component={CoreAssemblyDeleteDialog}
      />

      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/operation/interstice-assembly/new`}
        component={IntersticeAssemblyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/operation/interstice-assembly/:id/edit`}
        component={IntersticeAssemblyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefixToStrandSupplyId}/:strand_id/operation/interstice-assembly/:id/delete`}
        component={IntersticeAssemblyDeleteDialog}
      />

      <ErrorBoundaryRoute path={match.url} component={Study} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StudyDeleteDialog} />
  </>
);

export default Routes;
