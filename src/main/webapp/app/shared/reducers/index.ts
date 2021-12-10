import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import element from 'app/entities/element/element.reducer';
// prettier-ignore
import elementKind from 'app/entities/element-kind/element-kind.reducer';
// prettier-ignore
import copper from 'app/entities/copper/copper.reducer';
// prettier-ignore
import material from 'app/entities/material/material.reducer';
// prettier-ignore
import elementSupply from 'app/entities/element-supply/element-supply.reducer';
// prettier-ignore
import lifter from 'app/entities/lifter/lifter.reducer';
// prettier-ignore
import lifterRunMeasure from 'app/entities/lifter-run-measure/lifter-run-measure.reducer';
// prettier-ignore
import bangleSupply from 'app/entities/bangle-supply/bangle-supply.reducer';
// prettier-ignore
import bangle from 'app/entities/bangle/bangle.reducer';
// prettier-ignore
import elementKindEdition from 'app/entities/element-kind-edition/element-kind-edition.reducer';
// prettier-ignore
import materialMarkingStatistic from 'app/entities/material-marking-statistic/material-marking-statistic.reducer';
// prettier-ignore
import customComponentSupply from 'app/entities/custom-component-supply/custom-component-supply.reducer';
// prettier-ignore
import customComponent from 'app/entities/custom-component/custom-component.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  element,
  elementKind,
  copper,
  material,
  elementSupply,
  lifter,
  lifterRunMeasure,
  bangleSupply,
  bangle,
  elementKindEdition,
  materialMarkingStatistic,
  customComponentSupply,
  customComponent,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
