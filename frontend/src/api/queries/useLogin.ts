import { useMutation } from "react-query";
import { login } from "../auth.ts";
import { useNavigate } from "react-router-dom";

export const useLogin = () => {
  const navigation = useNavigate();
  return useMutation(login, {
    mutationKey: "login",
    onSuccess: (accessToken: string) => {
      localStorage.setItem("access_token", accessToken);
      navigation("/repos", { replace: true });
    },
    retry: false,
  });
};
