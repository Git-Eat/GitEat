import { GuideList } from "../../components/guideList";
import logo from "../../assets/images/logo_blue.svg";

export function Guide() {
  return (
    <div className="flex justify-center bg-guide-background m-auto px-8 py-4 my-1 rounded-2xl min-h-[calc(100vh-100px)]">
      <main className="w-[calc(100vw-250px)]">
        <header className="my-40 text-center font-bold">
          <img src={logo} alt="logo" className="w-16 mx-auto" />
          <h1 className="my-2 text-4xl text-guide-main">Git-Eat Guide</h1>
          <h2 className="mt-10 text-lg text-guide-text">
            Git-Eat과 함께 코드 리뷰를 시작해보세요.
          </h2>
          <article className="m-5 text-guide-light">
            <p className="pb-1">
              Git-Eat은 진행중인 프로젝트의 Merge Request 정보, AI 코드 리뷰,
            </p>
            <p>MatterMost 알림 등록, 대시보드, 성능 측정을 제공합니다.</p>
          </article>
        </header>
        <hr className="m-40" />
        <section>
          <article className="my-40">
            <GuideList
              title="Merge Request"
              content="프로젝트 참여자들과의 코드 리뷰 및 AI 코드 리뷰를 제공합니다."
            />
          </article>
          <article className="my-40">
            <GuideList
              title="Dashboard"
              content="프로젝트의 현황과 기여도를 제공합니다."
            />
          </article>
          <article className="my-40">
            <GuideList
              title="Frontend Statistics"
              content="Lighthouse의 성능 측정 결과를 제공합니다. (약 1분 소요)"
            />
          </article>
        </section>
      </main>
    </div>
  );
}
