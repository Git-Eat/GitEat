import { Header } from "../../components/pullRequest/header";
import { TotalCommits } from "../../components/dashboard/totalCommits";
import { Participants } from "../../components/dashboard/participants";
import { BarChartExample } from "../../components/dashboard/pRStatistics";
import { PieChart } from "../../components/dashboard/pieChart";
import { MixedChartByLine } from "../../components/dashboard/mixedChartByLine";
import { Suspense, useMemo } from "react";
import { ErrorBoundary } from "../../components/common/errorBoundery";
import { Skeleton } from "@mui/material";
import { useGetContributors } from "../../api/queries/useGetContributors";
import { useParams } from "react-router-dom";
import { Contributors as ContributorsType } from "../../api/types/DashBoard";

function getMaxValues(data: ContributorsType | null) {
  if (data === null) return;
  let maxCommit = 0;
  let maxComment = 0;
  let maxMergeRequest = 0;

  // 모든 기여자(contributors)를 순회하면서
  data.contributors.forEach((contributor) => {
    // 각 기여자의 weeklyInfo 배열을 순회합니다.
    contributor.weeklyInfo.forEach((info) => {
      if (info.commitCount > maxCommit) {
        maxCommit = info.commitCount;
      }
      if (info.commentCount > maxComment) {
        maxComment = info.commentCount;
      }
      if (info.mergeRequestCount > maxMergeRequest) {
        maxMergeRequest = info.mergeRequestCount;
      }
    });
  });

  return Math.max(maxCommit, maxComment, maxMergeRequest);
}
function Contributors() {
  const { repoId } = useParams();
  const { data } = useGetContributors(repoId as string);
  const maxValue = useMemo(() => getMaxValues(data ? data : null), [data]);
  return (
    <>
      {data?.contributors.map((contributor) => (
        <div className="w-[48%] mb-10" key={contributor.userId}>
          <MixedChartByLine data={contributor} maxValue={maxValue} />
        </div>
      ))}
    </>
  );
}

export function DashBoard() {
  const { title, owner } = useParams();
  return (
    <div>
      <Header title={title} owner={owner} />

      <main className="w-[98%] m-auto px-8 py-4 bg-stone-50 rounded-2xl min-h-[calc(100vh-100px)]">
        <div className="w-[72%] m-auto my-10">
          <h1 className=" text-2xl font-semibold flex gap-4 text-center pb-1">
            <span>DashBoard</span>
          </h1>
          <span className="block text-neutral-400 text-sm">
            <span className="text-black">{title}</span> 의 프로젝트 현황을
            확인해보세요🌟
          </span>
        </div>
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
            <span className="text-gray-400 mb-10">
              Commit, MR, Comment 통계를 통해 기여도를 확인하세요!
            </span>
            <div className="w-full flex flex-wrap justify-between ">
              <div className="w-[98%] flex flex-wrap justify-between">
                <ErrorBoundary fallbackComponent={<>error occured</>}>
                  <Suspense fallback={<Skeleton width="100%" height={500} />}>
                    <Contributors />
                  </Suspense>
                </ErrorBoundary>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
