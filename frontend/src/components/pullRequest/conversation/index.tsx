import { Comments } from "./comments";
import { MarkdownEditor } from "../../common/markdownEditor";
import { Reviewers } from "./reviewers";
import spinner from "../../../assets/images/spinner.svg";
import { useCreateComment } from "../../../api/queries/useCreateComment";
import { Suspense } from "react";
import { ErrorBoundary } from "../../common/errorBoundery";
import { useParams } from "react-router-dom";

export function Conversation() {
  const { baseRepoId, prId } = useParams();
  const numericRepoId = Number(baseRepoId);
  const numericPrId = Number(prId);
  const { mutate: createComment } = useCreateComment(
    numericRepoId,
    numericPrId
  );

  function handleAddComment(content: string, commentType: number) {
    if (!content.trim()) return;
    createComment({ content, commentType });
  }

  return (
    <section className="flex gap-5">
      <main className="w-3/4">
        <ErrorBoundary>
          <Suspense fallback={<img src={spinner} alt="Loading..." />}>
            <Comments repoId={numericRepoId} prId={numericPrId} />
          </Suspense>
        </ErrorBoundary>
        <MarkdownEditor
          onAddSingleComment={(content, commentType) => {
            handleAddComment(content, commentType);
          }}
          onStartReview={() => {}}
          onUpdateComment={() => {}}
        />
      </main>
      <aside className="w-1/4">
        <ErrorBoundary>
          <Suspense fallback={<img src={spinner} alt="Loading..." />}>
            <Reviewers repoId={numericRepoId} prId={numericPrId} />
          </Suspense>
        </ErrorBoundary>
      </aside>
    </section>
  );
}
