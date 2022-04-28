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
import StrandCompute from '../strand/strand-compute';
import StrandDeleteDialog from '../strand/strand-delete-dialog';
import SheathingUpdate from '../sheathing/sheathing-update';
import SheathingDeleteDialog from '../sheathing/sheathing-delete-dialog';
import { StrandSupplyAssemble } from '../strand-supply/strand-supply-assemble';
import { StrandSupplyAssemblyDeleteDialog } from '../strand-supply/strand-supply-assembly-delete';
import { TapeLayingUpdate } from '../tape-laying/tape-laying-update';
import { TapeLayingDeleteDialog } from '../tape-laying/tape-laying-delete-dialog';
import ScreenUpdate from '../screen/screen-update';
import ScreenDeleteDialog from '../screen/screen-delete-dialog';
import StripLayingUpdate from '../strip-laying/strip-laying-update';
import StripLayingDeleteDialog from '../strip-laying/strip-laying-delete-dialog';
import ContinuityWireLongitLayingUpdate from '../continuity-wire-longit-laying/continuity-wire-longit-laying-update';
import ContinuityWireLongitLayingDeleteDialog from '../continuity-wire-longit-laying/continuity-wire-longit-laying-delete-dialog';
import PlaitUpdate from '../plait/plait-update';
import PlaitDeleteDialog from '../plait/plait-delete-dialog';

const studySuppliesUrlPrefix = '/:study_id/study-supplies';

const strandSupplyZoneUrlPefix = studySuppliesUrlPrefix + '/strand-supply';

const strandZoneUrlPefix = studySuppliesUrlPrefix + '/strand';

const strandSupplyOperationZoneUrlPrefix = strandSupplyZoneUrlPefix + '/:strand_supply_id/operation';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StudyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StudyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StudyDetail} />

      {/* (CUD ACCESS): STRAND SUPPLIES */}
      <ErrorBoundaryRoute exact path={`${match.url + strandSupplyZoneUrlPefix}/:id/edit`} component={StrandSupplyUpdate} />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyOperationZoneUrlPrefix}/strand-supply/:id/edit`}
        component={StrandSupplyUpdate}
      />
      <ErrorBoundaryRoute exact path={`${match.url + strandSupplyZoneUrlPefix}/:id/delete`} component={StrandSupplyDeleteDialog} />

      {/* (CUD ACCESS): STRANDS */}
      <ErrorBoundaryRoute exact path={`${match.url + strandZoneUrlPefix}/:id/edit`} component={StrandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url + strandZoneUrlPefix}/:id/delete`} component={StrandDeleteDialog} />

      {/* (1): Creating directly a Strand in a StrandSupply sub menu */}
      <ErrorBoundaryRoute exact path={`${match.url + studySuppliesUrlPrefix}`} component={StudyStrandSupply} />

      {/* (2): Strand compute = StrandSupply Creation */}
      <ErrorBoundaryRoute exact path={`${match.url + strandZoneUrlPefix}/:strand_id/strand-compute`} component={StrandCompute} />

      {/* STRAND SUPPLY ZONE */}

      {/* (4): Creating directly a Strand in a StrandSupply sub menu */}
      <ErrorBoundaryRoute exact path={`${match.url + studySuppliesUrlPrefix}/strand/new`} component={StrandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url + studySuppliesUrlPrefix}/:strand_supply_id/strand/new`} component={StrandUpdate} />

      {/* (5): Strand's sub-supply and sub-operations observing */}
      {/* STRAND'S SUB SUPPLY ZONE */}
      <ErrorBoundaryRoute exact path={`${match.url + strandZoneUrlPefix}/:id/supply`} component={StrandSubSupply} />

      <ErrorBoundaryRoute exact path={`${match.url + strandSupplyOperationZoneUrlPrefix}`} component={StrandSupplySubOperation} />

      {/* [NON_ASSEMBLY_OPERATION] (CUD ACCESS) */}
      <ErrorBoundaryRoute exact path={`${match.url + strandSupplyOperationZoneUrlPrefix}/tape-laying/new`} component={TapeLayingUpdate} />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyOperationZoneUrlPrefix}/tape-laying/:id/edit`}
        component={TapeLayingUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyOperationZoneUrlPrefix}/tape-laying/:id/delete`}
        component={TapeLayingDeleteDialog}
      />

      <ErrorBoundaryRoute exact path={`${match.url + strandSupplyOperationZoneUrlPrefix}/screen/new`} component={ScreenUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url + strandSupplyOperationZoneUrlPrefix}/screen/:id/edit`} component={ScreenUpdate} />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyOperationZoneUrlPrefix}/screen/:id/delete`}
        component={ScreenDeleteDialog}
      />

      <ErrorBoundaryRoute exact path={`${match.url + strandSupplyOperationZoneUrlPrefix}/strip-laying/new`} component={StripLayingUpdate} />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyOperationZoneUrlPrefix}/strip-laying/:id/edit`}
        component={StripLayingUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyOperationZoneUrlPrefix}/strip-laying/:id/delete`}
        component={StripLayingDeleteDialog}
      />

      <ErrorBoundaryRoute exact path={`${match.url + strandSupplyOperationZoneUrlPrefix}/plait/new`} component={PlaitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url + strandSupplyOperationZoneUrlPrefix}/plait/:id/edit`} component={PlaitUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url + strandSupplyOperationZoneUrlPrefix}/plait/:id/delete`} component={PlaitDeleteDialog} />

      <ErrorBoundaryRoute exact path={`${match.url + strandSupplyOperationZoneUrlPrefix}/sheathing/new`} component={SheathingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url + strandSupplyOperationZoneUrlPrefix}/sheathing/:id/edit`} component={SheathingUpdate} />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyOperationZoneUrlPrefix}/sheathing/:id/delete`}
        component={SheathingDeleteDialog}
      />

      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyOperationZoneUrlPrefix}/continuity-wire-longit-laying/new`}
        component={ContinuityWireLongitLayingUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyOperationZoneUrlPrefix}/continuity-wire-longit-laying/:id/edit`}
        component={ContinuityWireLongitLayingUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyOperationZoneUrlPrefix}/continuity-wire-longit-laying/:id/delete`}
        component={ContinuityWireLongitLayingDeleteDialog}
      />

      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyOperationZoneUrlPrefix}/assemblies/new`}
        component={StrandSupplyAssemble}
      />

      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyOperationZoneUrlPrefix}/assemblies/delete`}
        component={StrandSupplyAssemblyDeleteDialog}
      />

      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandZoneUrlPefix}/:strand_id/supply/custom-component-supply/new`}
        component={CustomComponentSupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandZoneUrlPefix}/:strand_id/supply/custom-component-supply/:id/edit`}
        component={CustomComponentSupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandZoneUrlPefix}/:strand_id/supply/custom-component-supply/:id/delete`}
        component={CustomComponentSupplyDeleteDialog}
      />

      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandZoneUrlPefix}/:strand_id/supply/bangle-supply/new`}
        component={BangleSupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandZoneUrlPefix}/:strand_id/supply/bangle-supply/:id/edit`}
        component={BangleSupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandZoneUrlPefix}/:strand_id/supply/bangle-supply/:id/delete`}
        component={BangleSupplyDeleteDialog}
      />

      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandZoneUrlPefix}/:strand_id/supply/element-supply/new`}
        component={ElementSupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandZoneUrlPefix}/:strand_id/supply/element-supply/:id/edit`}
        component={ElementSupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandZoneUrlPefix}/:strand_id/supply/element-supply/:id/delete`}
        component={ElementSupplyDeleteDialog}
      />

      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandZoneUrlPefix}/:strand_id/supply/one-study-supply/new`}
        component={OneStudySupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandZoneUrlPefix}/:strand_id/supply/one-study-supply/:id/edit`}
        component={OneStudySupplyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandZoneUrlPefix}/:strand_id/supply/one-study-supply/:id/delete`}
        component={OneStudySupplyDeleteDialog}
      />

      {/* STRANDSUPPLY'S SUB OPERATION ZONE */}
      <ErrorBoundaryRoute exact path={`${match.url + strandSupplyZoneUrlPefix}/:id/operation`} component={StrandSupplySubOperation} />

      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefix}/:strand_id/operation/central-assembly/new`}
        component={CentralAssemblyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefix}/:strand_id/operation/central-assembly/:id/edit`}
        component={CentralAssemblyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefix}/:strand_id/operation/central-assembly/:id/delete`}
        component={CentralAssemblyDeleteDialog}
      />

      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefix}/:strand_id/operation/core-assembly/new`}
        component={CoreAssemblyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefix}/:strand_id/operation/core-assembly/:id/edit`}
        component={CoreAssemblyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefix}/:strand_id/operation/core-assembly/:id/delete`}
        component={CoreAssemblyDeleteDialog}
      />

      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefix}/:strand_id/operation/interstice-assembly/new`}
        component={IntersticeAssemblyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefix}/:strand_id/operation/interstice-assembly/:id/edit`}
        component={IntersticeAssemblyUpdate}
      />
      <ErrorBoundaryRoute
        exact
        path={`${match.url + strandSupplyZoneUrlPefix}/:strand_id/operation/interstice-assembly/:id/delete`}
        component={IntersticeAssemblyDeleteDialog}
      />

      <ErrorBoundaryRoute path={match.url} component={Study} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StudyDeleteDialog} />
  </>
);

export default Routes;
