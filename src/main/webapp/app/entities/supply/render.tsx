import React from 'react';
import { IAbstractSupply } from 'app/shared/model/abstract-supply.model';
import { Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { Color } from 'app/shared/model/enumerations/color.model';
import { MarkingTechnique } from 'app/shared/model/enumerations/marking-technique.model';

export function renderSupplyTds(
  supply: any, // IAbstractSupply,
  match: { url: string }
) {
  return (
    <>
      <td>{supply.apparitions}</td>
      <td>{supply.meterQuantity}</td>
      <td>{supply.cylindricComponent?.articleNumber}</td>
      <td>{supply.designation}</td>
      <td>{supply.description}</td>
      <td>{'markingType' in supply ? <Translate contentKey={`lappLiApp.MarkingType.${supply.markingType}`} /> : ''}</td>
      <td>{'markingTechnique' in supply ? <Translate contentKey={`lappLiApp.MarkingTechnique.${supply.markingTechnique}`} /> : ''}</td>
      <td>{supply.cylindricComponent?.gramPerMeterLinearMass}</td>
      <td>{supply.cylindricComponent?.milimeterDiameter}</td>
      <td>{'surfaceColor' in supply ? <Translate contentKey={`lappLiApp.Color.${supply.surfaceColor}`} /> : ''}</td>
      <td>
        {supply.surfaceMaterial ? <Link to={`material/${supply.surfaceMaterial.id}`}>{supply.surfaceMaterial.designation}</Link> : ''}
      </td>
      <td>{'markingType' in supply ? supply.bestLiftersNames : ''}</td>
      <td>{supply.formatedHourPreparationTime}</td>
      <td>{supply.formatedHourExecutionTime}</td>
      <td>{supply.meterPerHourSpeed}</td>
      <td className="text-right">
        <div className="btn-group flex-btn-group-container">
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
    </>
  );
}
