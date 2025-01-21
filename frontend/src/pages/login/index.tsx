export function Login() {
  return (
    <div className='w-[60%] mx-auto flex items-center justify-center h-screen justify-between'>
      <section>
        <div>
          <h1 className='text-5xl font-bold mb-[25px]'>
            GIT
            <img
              className='inline w-[45px]'
              src='/src/assets/images/logo.svg'
              alt='logo'
            />
            EAT
          </h1>
          <article className='flex flex-col gap-[5px] text-[#5C5C5D] text-xl tracking-wide mb-[50px]'>
            <span>
              <strong>Git-Eat</strong>은 코드리뷰 문화를 처음 접하는 주니어
              개발자들에게
            </span>
            <span>
              온보딩 플랫폼을 제공하는 서비스입니다.{' '}
              <strong>AI 코드리뷰</strong>를 통해
            </span>
            <span>효율적인 코드리뷰를 진행해보세요 ! Let’s Git-Eat!</span>
          </article>
          <div className='w-[370px] flex flex-col gap-[18px]'>
            <a
              href='/login/github'
              className='h-[71px] flex gap-[9px] bg-black text-white items-center justify-center font-[20px] font-semibold rounded-[30px]'
            >
              <img
                className='w-[55px]'
                src='/src/assets/images/github_logo.svg'
                alt='github_logo'
              />
              GitHub로 시작하기
            </a>
            <a
              href='/login/github'
              className='h-[71px] flex gap-[9px] bg-[#364CCA] text-white items-center justify-center font-[20px] font-semibold rounded-[30px]'
            >
              <img src='/src/assets/images/gitlab_logo.svg' alt='github_logo' />
              GitLab으로 시작하기
            </a>
          </div>
        </div>
      </section>
      <img src='/src/assets/images/main_side.png' alt='side' />
    </div>
  );
}
