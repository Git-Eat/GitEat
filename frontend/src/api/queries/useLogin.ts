import { useMutation } from "react-query";
import { AuthToken } from "../types/Auth.ts";
import { login } from "../auth.ts";

export const useLogin = () => {
  return useMutation(login, {
    mutationKey: "login",
    onSuccess: (data: AuthToken) => {
      localStorage.setItem("token", data.accessToken);
      window.location.replace("/dashboard");
    },
  });
};
