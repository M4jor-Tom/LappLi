import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Element from './element';
import ElementKind from './element-kind';
import Copper from './copper';
import Material from './material';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}element`} component={Element} />
      <ErrorBoundaryRoute path={`${match.url}element-kind`} component={ElementKind} />
      <ErrorBoundaryRoute path={`${match.url}copper`} component={Copper} />
      <ErrorBoundaryRoute path={`${match.url}material`} component={Material} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
