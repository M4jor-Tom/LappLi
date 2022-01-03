import React from 'react';
import { IAbstractSupply } from 'app/shared/model/abstract-supply.model';
import { Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { Color } from 'app/shared/model/enumerations/color.model';

export function renderSupplyTr(
  supply: IAbstractSupply,
  index: number,
  match: { url: string },
  markingType: MarkingType | null,
  surfaceColor: Color | null,
  bestLiftersNames: string | null
) {
  return (
    <tr key={`entity-${index}`} data-cy="entityTable">
      <td>
        <Button tag={Link} to={`${match.url}/${supply.id}`} color="link" size="sm">
          {supply.id}
        </Button>
      </td>
      <td>{supply.apparitions}</td>
      <td>{supply.cylindricComponent?.articleNumber}</td>
      <td>{supply.designation}</td>
      <td>{supply.description}</td>
      <td>{markingType != null ? <Translate contentKey={`lappLiApp.MarkingType.${markingType}`} /> : ''}</td>
      <td>{supply.cylindricComponent?.gramPerMeterLinearMass}</td>
      <td>{supply.cylindricComponent?.milimeterDiameter}</td>
      <td>{surfaceColor != null ? <Translate contentKey={`lappLiApp.Color.${surfaceColor}`} /> : ''}</td>
      <td>
        {supply.surfaceMaterial ? <Link to={`material/${supply.surfaceMaterial.id}`}>{supply.surfaceMaterial.designation}</Link> : ''}
      </td>
      <td>{bestLiftersNames != null ? { bestLiftersNames } : ''}</td>
      <td>{supply.strand ? <Link to={`strand/${supply.strand.id}`}>{supply.strand.designation}</Link> : ''}</td>
      <td className="text-right">
        <div className="btn-group flex-btn-group-container">
          <Button tag={Link} to={`${match.url}/${supply.id}`} color="info" size="sm" data-cy="entityDetailsButton">
            <FontAwesomeIcon icon="eye" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.view">View</Translate>
            </span>
          </Button>
          <Button tag={Link} to={`${match.url}/${supply.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
          <Button tag={Link} to={`${match.url}/${supply.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
            <FontAwesomeIcon icon="trash" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.delete">Delete</Translate>
            </span>
          </Button>
        </div>
      </td>
    </tr>
  );
}
