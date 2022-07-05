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
// prettier-ignore
import strand from 'app/entities/strand/strand.reducer';
// prettier-ignore
import oneStudySupply from 'app/entities/one-study-supply/one-study-supply.reducer';
// prettier-ignore
import study from 'app/entities/study/study.reducer';
// prettier-ignore
import strandSupply from 'app/entities/strand-supply/strand-supply.reducer';
// prettier-ignore
import userData from 'app/entities/user-data/user-data.reducer';
// prettier-ignore
import centralAssembly from 'app/entities/central-assembly/central-assembly.reducer';
// prettier-ignore
import coreAssembly from 'app/entities/core-assembly/core-assembly.reducer';
// prettier-ignore
import intersticeAssembly from 'app/entities/interstice-assembly/interstice-assembly.reducer';
// prettier-ignore
import sheathing from 'app/entities/sheathing/sheathing.reducer';
// prettier-ignore
import supplyPosition from 'app/entities/supply-position/supply-position.reducer';
// prettier-ignore
import tape from 'app/entities/tape/tape.reducer';
// prettier-ignore
import tapeKind from 'app/entities/tape-kind/tape-kind.reducer';
// prettier-ignore
import tapeLaying from 'app/entities/tape-laying/tape-laying.reducer';
// prettier-ignore
import screen from 'app/entities/screen/screen.reducer';
// prettier-ignore
import copperFiber from 'app/entities/copper-fiber/copper-fiber.reducer';
// prettier-ignore
import strip from 'app/entities/strip/strip.reducer';
// prettier-ignore
import stripLaying from 'app/entities/strip-laying/strip-laying.reducer';
// prettier-ignore
import continuityWireLongitLaying from 'app/entities/continuity-wire-longit-laying/continuity-wire-longit-laying.reducer';
// prettier-ignore
import continuityWire from 'app/entities/continuity-wire/continuity-wire.reducer';
// prettier-ignore
import plait from 'app/entities/plait/plait.reducer';
// prettier-ignore
import steelFiber from 'app/entities/steel-fiber/steel-fiber.reducer';
// prettier-ignore
import carrierPlait from 'app/entities/carrier-plait/carrier-plait.reducer';
// prettier-ignore
import carrierPlaitFiber from 'app/entities/carrier-plait-fiber/carrier-plait-fiber.reducer';
// prettier-ignore
import plaiter from 'app/entities/plaiter/plaiter.reducer';
// prettier-ignore
import plaiterConfiguration from 'app/entities/plaiter-configuration/plaiter-configuration.reducer';
// prettier-ignore
import flatSheathing from 'app/entities/flat-sheathing/flat-sheathing.reducer';
// prettier-ignore
import myNewComponent from 'app/entities/my-new-component/my-new-component.reducer';
// prettier-ignore
import myNewComponentSupply from 'app/entities/my-new-component-supply/my-new-component-supply.reducer';
// prettier-ignore
import myNewOperation from 'app/entities/my-new-operation/my-new-operation.reducer';
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
  strand,
  oneStudySupply,
  study,
  strandSupply,
  userData,
  centralAssembly,
  coreAssembly,
  intersticeAssembly,
  sheathing,
  supplyPosition,
  tape,
  tapeKind,
  tapeLaying,
  screen,
  copperFiber,
  strip,
  stripLaying,
  continuityWireLongitLaying,
  continuityWire,
  plait,
  steelFiber,
  carrierPlait,
  carrierPlaitFiber,
  plaiter,
  plaiterConfiguration,
  flatSheathing,
  myNewComponent,
  myNewComponentSupply,
  myNewOperation,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
