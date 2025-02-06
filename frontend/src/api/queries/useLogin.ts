import { useMutation } from "react-query";
import { AuthToken } from "../types/Auth.ts";
import { login } from "../auth.ts";

export const useLogin = () => {
  return useMutation(login, {
    mutationKey: "login",
    onSuccess: (data: AuthToken) => {
      alert("login success");
      localStorage.setItem("access_token", data.accessToken);
      window.location.replace("/dashboard");
    },
    retry: false,
  });
};
