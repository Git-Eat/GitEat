import { useState } from "react";

export const useBooleanState = (
  initialState: boolean
): [boolean, () => void, () => void, () => void] => {
  const [state, setState] = useState<boolean>(initialState);
  const setTrue = () => setState(true);
  const setFalse = () => setState(false);
  const setReverse = () => setState(!state);
  return [state, setTrue, setFalse, setReverse];
};
