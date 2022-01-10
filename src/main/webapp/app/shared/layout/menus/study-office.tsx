import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const StudyOfficeMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.studyOffice')}
    id="study-office-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/study">
      <Translate contentKey="global.menu.entities.study" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bangle">
      <Translate contentKey="global.menu.entities.bangle" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/custom-component">
      <Translate contentKey="global.menu.entities.customComponent" />
    </MenuItem>
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
    <MenuItem icon="asterisk" to="/element-kind-edition">
      <Translate contentKey="global.menu.entities.elementKindEdition" />
    </MenuItem>
  </NavDropdown>
);
