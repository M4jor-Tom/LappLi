import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { NavLink as Link } from 'react-router-dom';
import { Translate, translate } from 'react-jhipster';
import { NavItem } from 'reactstrap';
import { NavLink } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export const StudyMultiSupplyListNavBarItem = props => (
  <NavItem icon="cable-icon">
    <NavLink to="/study-multi-supply-list" className="d-flex align-items-center">
      <span>
        <Translate contentKey="global.menu.study-multi-supply-list">Supply List</Translate>
      </span>
    </NavLink>
  </NavItem>
);
