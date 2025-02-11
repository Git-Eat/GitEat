import { Outlet, useLocation, useParams } from "react-router-dom";
import { Header } from "../../components/pullRequest/header";
import { PrHeader } from "../../components/pullRequest/prHeader";
import { ErrorBoundary } from "../../components/common/errorBoundery";
import { Suspense, useEffect } from "react";
import { useGetPullRequest } from "../../api/queries/useGetPullRequest";
import { useGetFileChanges } from "../../api/queries/useGetFileChanges";
import { useGetComments } from "../../api/queries/useGetComments";
import { usePRStore } from "../../store/pullRequestStore";

function DataProvider() {
  const { baseRepoId, prId } = useParams();
  const { setComments, setFiles } = usePRStore();
  const { data: pullRequest } = useGetPullRequest(
    Number(baseRepoId),
    Number(prId)
  );
  const { data: fileList, isLoading: isFileLoading } = useGetFileChanges(
    Number(baseRepoId),
    Number(prId)
  );
  useEffect(() => {
    if (!isFileLoading && fileList) {
      setFiles(fileList);
    }
  }, [isFileLoading]);
  const { data: commentList, isLoading: isCommentLoading } = useGetComments(
    Number(baseRepoId),
    Number(prId)
  );
  useEffect(() => {
    if (!isCommentLoading && commentList) {
      setComments(commentList);
    }
  }, [isCommentLoading]);
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
