import logo from "../../assets/images/logo.svg";
import githubLogo from "../../assets/images/github_logo.svg";
import gitlabLogo from "../../assets/images/gitlab_logo.svg";
import side from "../../assets/images/main_side.png";
// import { useGetMe } from "../../api/queries/useGetMe";
// import { useNavigate } from "react-router-dom";
// import { useEffect } from "react";

export function Login() {
  const gitLabLogin = () => {
    const REDIRECT_URI = import.meta.env.VITE_OAUTH_REDIRECT;
    const CLIENT_ID = import.meta.env.VITE_GITLAB_CLIENT_ID;
    const gitLabAuthUrl = `https://lab.ssafy.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;
    window.location.href = gitLabAuthUrl;
  };
  // const { data, isLoading } = useGetMe();
  // const navigation = useNavigate();
  // useEffect(() => {
  //   if (!isLoading && data) {
  //     navigation("/repos");
  //   }
  // }, [data, isLoading]);
  // if (isLoading) return <>loading</>;
  return (
    <div className="w-[75%] mx-auto flex items-center justify-center h-screen justify-between">
      <section>
        <div>
          <h1 className="text-5xl font-bold mb-[25px]">
            GIT
            <img className="inline w-[45px]" src={logo} alt="logo" />
            EAT
          </h1>
          <article className="flex flex-col gap-[5px] text-[#5C5C5D] text-xl tracking-wide mb-[50px]">
            <span>
              <strong>Git-Eat</strong>은 코드리뷰 문화를 처음 접하는 주니어
              개발자들에게
            </span>
            <span>
              온보딩 플랫폼을 제공하는 서비스입니다.{" "}
              <strong>AI 코드리뷰</strong>를 통해
            </span>
            <span>효율적인 코드리뷰를 진행해보세요 ! Let’s Git-Eat!</span>
          </article>
          <div className="w-[370px] flex flex-col gap-[18px]">
            <a
              href="/loading"
              className="h-[71px] flex gap-[9px] bg-black text-white items-center justify-center font-[20px] font-semibold rounded-[30px]"
            >
              <img className="w-[55px]" src={githubLogo} alt="github_logo" />
              GitHub로 시작하기
            </a>
            <button
              onClick={gitLabLogin}
              className="h-[71px] flex gap-[9px] bg-[#364CCA] text-white items-center justify-center font-[20px] font-semibold rounded-[30px]"
            >
              <img src={gitlabLogo} alt="gitlab_logo" />
              GitLab으로 시작하기
            </button>
          </div>
        </div>
      </section>

      <img src={side} alt="side" />
    </div>
  );
}
