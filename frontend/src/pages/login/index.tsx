export function Login() {
  // 백엔드에서 처리할 리디렉션 URI 추가해야댐

  const gitLabLogin = () => {
    const REDIRECT_URI = "http://127.0.0.1:5173/loading";
    const CLIENT_ID = import.meta.env.VITE_GITLAB_CLIENT_ID;
    //state 값은 랜덤값으로 변경 필요함
    const STATE = "1234";
    // 깃랩 인증 URL 생성
    const gitLabAuthUrl = `https://lab.ssafy.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code&state=${STATE}&scope=read_user`;
    // 인증 페이지로 리다이렉트
    window.location.href = gitLabAuthUrl;
  };
  return (
    <div className="w-[75%] mx-auto flex items-center justify-center h-screen justify-between">
      <section>
        <div>
          <h1 className="text-5xl font-bold mb-[25px]">
            GIT
            <img
              className="inline w-[45px]"
              src="/src/assets/images/logo.svg"
              alt="logo"
            />
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
              <img
                className="w-[55px]"
                src="/src/assets/images/github_logo.svg"
                alt="github_logo"
              />
              GitHub로 시작하기
            </a>
            <button
              onClick={gitLabLogin}
              className="h-[71px] flex gap-[9px] bg-[#364CCA] text-white items-center justify-center font-[20px] font-semibold rounded-[30px]"
            >
              <img src="/src/assets/images/gitlab_logo.svg" alt="github_logo" />
              GitLab으로 시작하기
            </button>
          </div>
        </div>
      </section>
      <img src="/src/assets/images/main_side.png" alt="side" />
    </div>
  );
}
