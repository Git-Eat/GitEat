import { useMutation } from "react-query";
import { AuthToken } from "../types/Auth.ts";
import { login } from "../auth.ts";
import { useNavigate } from "react-router-dom";

export const useLogin = () => {
  const navigation = useNavigate();
  return useMutation(login, {
    mutationKey: "login",
    onSuccess: (data: AuthToken) => {
      localStorage.setItem("access_token", data.accessToken);
      navigation("/dashboard", { replace: true });
    },
    retry: false,
  });
};
