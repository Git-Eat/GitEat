import { Suspense } from "react";
import { Comments } from "./comments";
import { MarkdownEditor } from "../../common/markdownEditor";
import { ErrorBoundery } from "../../common/errorBoundery";
import { Reviewers } from "./reviewers";
import spinner from "../../../assets/images/spinner.svg";
import { useCreateComment } from "../../../api/queries/useCreateComment";

export function Conversation() {
  /* repoId & prId 변경 예정 */
  const repoId = 888788;
  const prId = 32;
  const { mutate: createComment } = useCreateComment(repoId, prId);

  function handleAddComment(content: string, commentType: 0 | 1 | 2) {
    if (!content.trim()) return;
    createComment({ content, commentType });
  }

  return (
    <section className="flex gap-5">
      <main className="w-3/4">
        <ErrorBoundery>
          <Suspense fallback={<img src={spinner} alt="Loading..." />}>
            <Comments repoId={repoId} prId={prId} />
          </Suspense>
        </ErrorBoundery>
        <MarkdownEditor
          onAddSingleComment={(content, commentType) => {
            handleAddComment(content, commentType);
          }}
          onStartReview={() => {}}
        />
      </main>
      <aside className="w-1/4">
        <ErrorBoundery>
          <Suspense fallback={<img src={spinner} alt="Loading..." />}>
            <Reviewers repoId={repoId} prId={prId} />
          </Suspense>
        </ErrorBoundery>
      </aside>
    </section>
  );
}
