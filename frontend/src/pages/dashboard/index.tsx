import { Header } from "../../components/pullRequest/header";
import { TotalCommits } from "../../components/dashboard/totalCommits";

export function DashBoard() {
  return (
    <div>
      <Header title={"asdf"} owner={"asdfasdf"} />

      <main className="w-[98%] m-auto px-8 py-4 bg-gray-100 rounded-2xl min-h-[calc(100vh-100px)]">
        <div className="w-[80%] m-auto">
          <h1 className=" text-2xl font-semibold flex gap-4 text-center pb-1">
            <span>DashBoard</span>
          </h1>
          <span className="block text-neutral-400 text-sm">
            <span className="text-black">asdf</span> 의 프로젝트 현황을
            확인해보세요!
          </span>
          <TotalCommits />
        </div>
      </main>
    </div>
  );
}
