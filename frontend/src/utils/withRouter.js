import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

/* TODO: 왜 이렇게 귀찮게 함?? */
function useQuery() {
  const { search } = useLocation();

  return React.useMemo(() => new URLSearchParams(search), [search]);
}

export const withRouterHook = (Component) => {
  const Wrapper = (props) => {
    const query = useQuery();
    const { state } = useLocation();
    const navigate = useNavigate();

    return <Component navigate={navigate} locationState={state} query={query} {...props} />;
  };

  return Wrapper;
};
