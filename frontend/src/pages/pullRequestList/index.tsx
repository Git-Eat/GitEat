import branchOpen from "../../assets/images/branch_open.svg";
import branchClose from "../../assets/images/branch_close.svg";
import branchMerge from "../../assets/images/branch_merge.svg";
import { Suspense, useMemo } from "react";
import { useGetPullRequests } from "../../api/queries/useGetPullRequests";
import book from "../../assets/images/image.png";
import { PullRequestCard } from "../../components/pullRequestList/pullRequestCard";
import { ErrorBoundary } from "../../components/common/errorBoundery";
import { useParams } from "react-router-dom";

const BRANCH_STATE_IMAGE = [branchOpen, branchMerge, branchClose];

function PullRequests() {
  const { repoId } = useParams();
  const { data } = useGetPullRequests(Number(repoId));
  const { totalOpen, totalClosed, totalMerged } = useMemo(() => {
    let open = 0;
    let closed = 0;
    let merged = 0;
    if (data) {
      data.forEach((pr) => {
        if (pr.isOpened === 0) {
          open++;
        } else if (pr.isOpened === 1) {
          closed++;
        } else if (pr.isOpened === 2) {
          merged++;
        }
      });
    }
    return { totalOpen: open, totalClosed: closed, totalMerged: merged };
  }, [data]);
  return (
    <>
      <div className=" bg-white rounded-xl p-7 flex gap-2 hover:bg-gray-200 cursor-pointer">
        <div className="flex items-center gap-2">
          <img src={BRANCH_STATE_IMAGE[0]} alt="open" />
          <div className="flex gap-1">
            <span>{totalOpen}</span>
            <span>Open</span>
          </div>
        </div>
        <div className="flex items-center gap-2">
          <img src={BRANCH_STATE_IMAGE[1]} alt="merge" />
          <div className="flex gap-1">
            <span>{totalClosed}</span>
            <span>Close</span>
          </div>
        </div>
        <div className="flex items-center gap-1">
          <img src={BRANCH_STATE_IMAGE[2]} alt="close" />
          <div className="flex gap-1">
            <span>{totalMerged}</span>
            <span>Merge</span>
          </div>
        </div>
      </div>
      {data?.map((pr) => (
        <PullRequestCard
          key={pr.prId}
          prId={pr.prId}
          title={pr.title}
          description={pr.description}
          createAt={pr.createAt}
          isOpened={pr.isOpened}
        />
      ))}
    </>
  );
}

export function PullRequestList() {
  return (
    <>
      <header className="w-full p-4">
        <div className="flex items-center gap-2 align-center">
          <img src={book} alt="pull request page" className="w-[18px]" />
          <h1 className="text-[18px] font-semibold flex gap-2 text-center pb-1">
            <span>TakeGitEasy</span>
            <span>/</span>
            <span>Git-Eat</span>
          </h1>
        </div>
        <div></div>
      </header>
      <main className=" w-[98%] m-auto px-8 py-4 bg-gray-100 rounded-2xl min-h-[calc(100vh-100px)]">
        <div className="flex flex-col gap-5 m-auto w-[80%] pt-10">
          <ErrorBoundary
            fallbackComponent={
              <p className="text-red-500">
                ⚠️ 프로젝트 목록을 불러오는 중 오류 발생
              </p>
            }
          >
            <Suspense fallback={<>loading</>}>
              <PullRequests />
            </Suspense>
          </ErrorBoundary>
        </div>
      </main>
    </>
  );
}
