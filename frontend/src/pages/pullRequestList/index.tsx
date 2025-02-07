import { Suspense } from "react";
import { useGetPullRequests } from "../../api/queries/useGetPullRequests";
import book from "../../assets/images/image.png";
import { PullRequestCard } from "../../components/pullRequestList/pullRequestCard";
import { ErrorBoundary } from "../../components/common/errorBoundery";
import { useParams } from "react-router-dom";

function PullRequests() {
  const { repoId } = useParams();
  console.log(repoId, Number(repoId));
  const { data } = useGetPullRequests(Number(repoId));
  return (
    <>
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
