import { useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';

function isStrandSupply(props: RouteComponentProps<{ strand_id: string; id: string }>): string {
  const [ret] = useState(props.match.params && props.match.params.strand_id);
  return ret;
}

function getRedirectionUrl(props: RouteComponentProps<{ strand_id: string; id: string }>, ifNotStrandSupply: string): string {
  return isStrandSupply(props) ? '/strand/' + props.match.params.strand_id + '/supply' : ifNotStrandSupply;
}

export { isStrandSupply, getRedirectionUrl };
