import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/element">
      <Translate contentKey="global.menu.entities.element" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/element-kind">
      <Translate contentKey="global.menu.entities.elementKind" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/copper">
      <Translate contentKey="global.menu.entities.copper" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/material">
      <Translate contentKey="global.menu.entities.material" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/element-supply">
      <Translate contentKey="global.menu.entities.elementSupply" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/lifter">
      <Translate contentKey="global.menu.entities.lifter" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/lifter-run-measure">
      <Translate contentKey="global.menu.entities.lifterRunMeasure" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bangle-supply">
      <Translate contentKey="global.menu.entities.bangleSupply" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bangle">
      <Translate contentKey="global.menu.entities.bangle" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/element-kind-edition">
      <Translate contentKey="global.menu.entities.elementKindEdition" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/material-marking-statistic">
      <Translate contentKey="global.menu.entities.materialMarkingStatistic" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/custom-component-supply">
      <Translate contentKey="global.menu.entities.customComponentSupply" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/custom-component">
      <Translate contentKey="global.menu.entities.customComponent" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/strand">
      <Translate contentKey="global.menu.entities.strand" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/one-study-supply">
      <Translate contentKey="global.menu.entities.oneStudySupply" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/study">
      <Translate contentKey="global.menu.entities.study" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/strand-supply">
      <Translate contentKey="global.menu.entities.strandSupply" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/user-data">
      <Translate contentKey="global.menu.entities.userData" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/central-assembly">
      <Translate contentKey="global.menu.entities.centralAssembly" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/core-assembly">
      <Translate contentKey="global.menu.entities.coreAssembly" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/interstice-assembly">
      <Translate contentKey="global.menu.entities.intersticeAssembly" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/position">
      <Translate contentKey="global.menu.entities.position" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/sheathing">
      <Translate contentKey="global.menu.entities.sheathing" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/supply-position">
      <Translate contentKey="global.menu.entities.supplyPosition" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/tape">
      <Translate contentKey="global.menu.entities.tape" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/tape-kind">
      <Translate contentKey="global.menu.entities.tapeKind" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/tape-laying">
      <Translate contentKey="global.menu.entities.tapeLaying" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/screen">
      <Translate contentKey="global.menu.entities.screen" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/copper-fiber">
      <Translate contentKey="global.menu.entities.copperFiber" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/strip">
      <Translate contentKey="global.menu.entities.strip" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/strip-laying">
      <Translate contentKey="global.menu.entities.stripLaying" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/metal-fiber">
      <Translate contentKey="global.menu.entities.metalFiber" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/continuity-wire-longit-laying">
      <Translate contentKey="global.menu.entities.continuityWireLongitLaying" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/continuity-wire">
      <Translate contentKey="global.menu.entities.continuityWire" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/plait">
      <Translate contentKey="global.menu.entities.plait" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/steel-fiber">
      <Translate contentKey="global.menu.entities.steelFiber" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/carrier-plait">
      <Translate contentKey="global.menu.entities.carrierPlait" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/carrier-plait-fiber">
      <Translate contentKey="global.menu.entities.carrierPlaitFiber" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/plaiter">
      <Translate contentKey="global.menu.entities.plaiter" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/plaiter-configuration">
      <Translate contentKey="global.menu.entities.plaiterConfiguration" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/flat-sheathing">
      <Translate contentKey="global.menu.entities.flatSheathing" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/flat-sheathing-supply-position">
      <Translate contentKey="global.menu.entities.flatSheathingSupplyPosition" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
