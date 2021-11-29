import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Element from './element';
import ElementKind from './element-kind';
import Copper from './copper';
import Material from './material';
import ElementSupply from './element-supply';
import Lifter from './lifter';
import LifterRunMeasure from './lifter-run-measure';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}element`} component={Element} />
      <ErrorBoundaryRoute path={`${match.url}element-kind`} component={ElementKind} />
      <ErrorBoundaryRoute path={`${match.url}copper`} component={Copper} />
      <ErrorBoundaryRoute path={`${match.url}material`} component={Material} />
      <ErrorBoundaryRoute path={`${match.url}element-supply`} component={ElementSupply} />
      <ErrorBoundaryRoute path={`${match.url}lifter`} component={Lifter} />
      <ErrorBoundaryRoute path={`${match.url}lifter-run-measure`} component={LifterRunMeasure} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
