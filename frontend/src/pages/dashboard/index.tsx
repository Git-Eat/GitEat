import { Header } from "../../components/pullRequest/header";
import { TotalCommits } from "../../components/dashboard/totalCommits";
import { Participants } from "../../components/dashboard/participants";
import { BarChartExample } from "../../components/dashboard/pRStatistics";
import { PieChart } from "../../components/dashboard/pieChart";
import { MixedChartByLine } from "../../components/dashboard/mixedChartByLine";
import { Suspense } from "react";
import { ErrorBoundary } from "../../components/common/errorBoundery";
import { Skeleton } from "@mui/material";

export function DashBoard() {
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
        <div className="w-[80%] m-auto flex flex-col gap-10">
          <div className="flex w-[90%] m-auto justify-between">
            <div className="w-[48%] h-[100px]">
              <ErrorBoundary fallbackComponent={<>error occured</>}>
                <Suspense fallback={<Skeleton width="100%" height="100%" />}>
                  <TotalCommits />
                </Suspense>
              </ErrorBoundary>
            </div>
            <div className="w-[48%] h-[100px]">
              <ErrorBoundary fallbackComponent={<>error occured</>}>
                <Suspense fallback={<Skeleton width="100%" height="100%" />}>
                  <Participants />
                </Suspense>
              </ErrorBoundary>
            </div>
          </div>
          <div className="flex w-[90%] m-auto justify-between">
            <div className="w-[48%] h-[500px]">
              <ErrorBoundary fallbackComponent={<>error occured</>}>
                <Suspense fallback={<Skeleton width="100%" height={500} />}>
                  <BarChartExample />
                </Suspense>
              </ErrorBoundary>
            </div>
            <div className="w-[48%]">
              <ErrorBoundary fallbackComponent={<>error occured</>}>
                <Suspense fallback={<Skeleton width="100%" height={500} />}>
                  <PieChart />
                </Suspense>
              </ErrorBoundary>
            </div>
          </div>
          <div className="flex flex-col w-[90%] m-auto justify-between bg-white border-lg p-12">
            <h2 className="text-xl font-bold">Contributors</h2>
            <span className="ps-5 text-gray-400">
              Commit, PR, Comment 통계를 통해 기여도를 확인하세요!
            </span>
            <div className="w-full flex flex-wrap justify-between ">
              <div className="w-[48%]">
                <MixedChartByLine />
              </div>
              <div className="w-[48%]">
                <MixedChartByLine />
              </div>
              <div className="w-[48%]">
                <MixedChartByLine />
              </div>
              <div className="w-[48%]">
                <MixedChartByLine />
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
