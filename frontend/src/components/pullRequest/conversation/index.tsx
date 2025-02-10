import { Comments } from "./comments";
import { MarkdownEditor } from "../../common/markdownEditor";
import { Reviewers } from "./reviewers";
import spinner from "../../../assets/images/spinner.svg";
import { useCreateComment } from "../../../api/queries/useCreateComment";
import { Suspense } from "react";
import { ErrorBoundary } from "../../common/errorBoundery";

export function Conversation() {
  /* repoId & prId 변경 예정 */
  const repoId = 888788;
  const prId = 6;
  const { mutate: createComment } = useCreateComment(repoId, prId);

  function handleAddComment(content: string, commentType: number) {
    if (!content.trim()) return;
    createComment({ content, commentType });
  }

  return (
    <section className="flex gap-5">
      <main className="w-3/4">
        <ErrorBoundary>
          <Suspense fallback={<img src={spinner} alt="Loading..." />}>
            <Comments repoId={repoId} prId={prId} />
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
            <Reviewers repoId={repoId} prId={prId} />
          </Suspense>
        </ErrorBoundary>
      </aside>
    </section>
  );
}
