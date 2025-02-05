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
      // 서버로 인증 코드 전송
      login(code, {
        onSuccess: () => {
          console.log("로그인 성공");
          // navigation("/repos");
        },
      });
    } else {
      navigation("/error");
    }
  }, [extractCodeFromUrl, login, navigation]);

  useEffect(() => {
    handleAuthentication();
  }, [handleAuthentication]);

  useEffect(() => {
    if (isError) {
      console.log(error);
      // window.location.replace("/error");
    }
  }, [isError]);

  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <img src="/src/assets/images/spinner.svg" alt="loading" />
      <span className="text-gray-400">인증중입니다. 잠시만 기다려주세요!</span>
    </div>
  );
}
