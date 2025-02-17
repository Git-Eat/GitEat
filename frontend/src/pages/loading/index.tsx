import spinner from "../../assets/images/spinner.svg";
import { useEffect } from "react";
import { useLogin } from "../../api/queries/useLogin";
import { useNavigate } from "react-router-dom";

export function Loading() {
  const { mutate: login, isError } = useLogin();
  const navigation = useNavigate();
  const extractCodeFromUrl = () => {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get("code");
  };
  const handleAuthentication = () => {
    const code = extractCodeFromUrl();
    if (code) {
      login(code);
    } else {
      navigation("/error");
    }
  };

  useEffect(() => {
    handleAuthentication();
  }, []);

  useEffect(() => {
    if (isError) {
      navigation("/error");
    }
  }, [isError]);

  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <img src={spinner} alt="loading" />
      <span className="text-gray-400">인증중입니다. 잠시만 기다려주세요!</span>
    </div>
  );
}
