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
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
