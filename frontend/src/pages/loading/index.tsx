import spinner from "../../assets/images/spinner.svg";
import { useCallback, useEffect } from "react";
import { useLogin } from "../../api/queries/useLogin";
import { useNavigate } from "react-router-dom";

export function Loading() {
  const { mutate: login, isError, error } = useLogin();
  const navigation = useNavigate();
  const extractCodeFromUrl = useCallback(() => {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get("code");
  }, []);
  const handleAuthentication = useCallback(() => {
    const code = extractCodeFromUrl();
    if (code) {
      alert("로그인 시도");
      login(code);
    } else {
      navigation("/error");
    }
  }, [extractCodeFromUrl, navigation]);

  useEffect(() => {
    handleAuthentication();
  }, []);

  useEffect(() => {
    if (isError) {
      console.log(error);
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
