import { Outlet, useLocation, useParams } from "react-router-dom";
import { Header } from "../../components/pullRequest/header";
import { PrHeader } from "../../components/pullRequest/prHeader";
import { ErrorBoundary } from "../../components/common/errorBoundery";
import { Suspense } from "react";
import { useGetPullRequest } from "../../api/queries/useGetPullRequest";

function DataProvider() {
  const { baseRepoId, prId } = useParams();
  const { data: pullRequest } = useGetPullRequest(
    Number(baseRepoId),
    Number(prId)
  );
  console.log(pullRequest);
  return (
    <div>
      <Header title={pullRequest?.title} owner={pullRequest?.userName} />

      <main className="w-[98%] m-auto px-8 py-4 bg-gray-100 rounded-2xl min-h-[calc(100vh-100px)]">
        <PrHeader
          userId={pullRequest!.userId}
          userName={pullRequest!.userName}
          sourceBranch={pullRequest!.sourceBranch}
          targetBranch={pullRequest!.targetBranch}
          prId={pullRequest!.prId}
          title={pullRequest!.title}
        />
        <Outlet />
      </main>
    </div>
  );
}

export function PullRequest() {
  const location = useLocation();
  return (
    <ErrorBoundary key={location.key} fallbackComponent={<>에러 발생!!</>}>
      <Suspense fallback={<>loading...</>}>
        <DataProvider />
      </Suspense>
    </ErrorBoundary>
  );
}
