import { LightHouseResult } from "../../components/dashboard/lighthouseResult";
import { Header } from "../../components/pullRequest/header";

export function FrontendStatistics() {
  return (
    <div>
      <Header title={"asdf"} owner={"asdfasdf"} />

      <main className="w-[98%] m-auto px-8 py-4 bg-gray-100 rounded-2xl min-h-[calc(100vh-100px)]">
        <h1 className=" text-2xl font-semibold flex gap-4 text-center pb-1">
          <span>DashBoard</span>
        </h1>
        <span className="block text-neutral-400 text-sm">
          <span className="text-black">asdf</span> 의 프로젝트 현황을
          확인해보세요!
        </span>
        <section className="my-5">
          {/* 임시 */}
          <LightHouseResult repoId={761731} />
        </section>
      </main>
    </div>
  );
}
