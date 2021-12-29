import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StrandSupply from './strand-supply';
import StrandSupplyDetail from './strand-supply-detail';
import StrandSupplyUpdate from './strand-supply-update';
import StrandSupplyDeleteDialog from './strand-supply-delete-dialog';
import StrandUpdate from '../strand/strand-update';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StrandSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StrandSupplyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StrandSupplyDetail} />

      {/* Creating directly a Strand in a StrandSupply sub menu */}
      {/* <ErrorBoundaryRoute exact path={`/strand-supply/strand/new`} component={StrandUpdate} />
      <ErrorBoundaryRoute exact path={`/strand-supply/:strand_supply_id/strand/new`} component={StrandUpdate} />*/}

      <ErrorBoundaryRoute path={match.url} component={StrandSupply} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StrandSupplyDeleteDialog} />
  </>
);

export default Routes;
