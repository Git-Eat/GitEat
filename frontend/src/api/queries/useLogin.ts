import { useMutation } from "react-query";
import { AuthToken } from "../types/Auth.ts";
import { login } from "../auth.ts";
import { useNavigate } from "react-router-dom";

export const useLogin = () => {
  const navagation = useNavigate();
  return useMutation(login, {
    mutationKey: "login",
    onSuccess: (data: AuthToken) => {
      alert("login success");
      localStorage.setItem("access_token", data.accessToken);
      navagation("/dashboard");
      // window.location.replace("/dashboard");
    },
    retry: false,
  });
};
