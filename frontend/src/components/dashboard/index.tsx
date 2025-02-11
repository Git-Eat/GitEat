import { FrontendStatics } from "./frontendStatistics";

export function DashBoard() {
  return (
    <>
      <header>
        <h1 className="text-[18px] font-semibold flex gap-2 text-center pb-1">
          DashBoard
        </h1>
        <div>
          <span>Git-eat</span>
          <span>의 프로젝트 현황을 확인해보세요!</span>
        </div>
      </header>
      <main>
        <FrontendStatics />
      </main>
    </>
  );
}
