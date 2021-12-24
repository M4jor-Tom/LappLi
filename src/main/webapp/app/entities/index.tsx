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
import BangleSupply from './bangle-supply';
import Bangle from './bangle';
import ElementKindEdition from './element-kind-edition';
import MaterialMarkingStatistic from './material-marking-statistic';
import CustomComponentSupply from './custom-component-supply';
import CustomComponent from './custom-component';
import Strand from './strand';
import OneStudySupply from './one-study-supply';
import Study from './study';
import StrandSupply from './strand-supply';
import UserData from './user-data';
import CentralAssembly from './central-assembly';
import CoreAssembly from './core-assembly';
import IntersticeAssembly from './interstice-assembly';
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
      <ErrorBoundaryRoute path={`${match.url}bangle-supply`} component={BangleSupply} />
      <ErrorBoundaryRoute path={`${match.url}bangle`} component={Bangle} />
      <ErrorBoundaryRoute path={`${match.url}element-kind-edition`} component={ElementKindEdition} />
      <ErrorBoundaryRoute path={`${match.url}material-marking-statistic`} component={MaterialMarkingStatistic} />
      <ErrorBoundaryRoute path={`${match.url}custom-component-supply`} component={CustomComponentSupply} />
      <ErrorBoundaryRoute path={`${match.url}custom-component`} component={CustomComponent} />
      <ErrorBoundaryRoute path={`${match.url}strand`} component={Strand} />
      <ErrorBoundaryRoute path={`${match.url}one-study-supply`} component={OneStudySupply} />
      <ErrorBoundaryRoute path={`${match.url}study`} component={Study} />
      <ErrorBoundaryRoute path={`${match.url}strand-supply`} component={StrandSupply} />
      <ErrorBoundaryRoute path={`${match.url}user-data`} component={UserData} />
      <ErrorBoundaryRoute path={`${match.url}central-assembly`} component={CentralAssembly} />
      <ErrorBoundaryRoute path={`${match.url}core-assembly`} component={CoreAssembly} />
      <ErrorBoundaryRoute path={`${match.url}interstice-assembly`} component={IntersticeAssembly} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
