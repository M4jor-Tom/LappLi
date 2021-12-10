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
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
