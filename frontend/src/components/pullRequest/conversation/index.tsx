import { Comments } from "./comments";
import { MarkdownEditor } from "../../common/markdownEditor";
import { Reviewers } from "./reviewers";
import spinner from "../../../assets/images/spinner.svg";
import { useCreateComment } from "../../../api/queries/useCreateComment";
import { Suspense } from "react";
import { ErrorBoundary } from "../../common/errorBoundery";
import { useParams } from "react-router-dom";
import { PullRequestInfo } from "./pullRequestInfo";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import { useGetAiReview } from "../../../api/queries/useGetAiReview";

export function Conversation() {
  const { baseRepoId, prId } = useParams();
  const numericRepoId = Number(baseRepoId);
  const numericPrId = Number(prId);
  const { data } = useGetAiReview(numericRepoId, numericPrId);
  const { mutate: createComment } = useCreateComment(
    numericRepoId,
    numericPrId
  );

  function handleAddComment(content: string) {
    if (!content.trim()) return;
    createComment({ content });
  }

  return (
    <section className="flex gap-5">
      <main className="w-3/4">
        <ErrorBoundary>
          <Suspense fallback={<img src={spinner} alt="Loading..." />}>
            <PullRequestInfo repoId={numericRepoId} prId={numericPrId} />
          </Suspense>
        </ErrorBoundary>
        <ErrorBoundary>
          <Suspense fallback={<img src={spinner} alt="Loading..." />}>
            {data?.map((review) => (
              <ReactMarkdown
                remarkPlugins={[remarkGfm]}
                key={review.aiReviewId}
              >
                {review.content}
              </ReactMarkdown>
            ))}
          </Suspense>
        </ErrorBoundary>
        <ErrorBoundary>
          <Suspense fallback={<img src={spinner} alt="Loading..." />}>
            <Comments repoId={numericRepoId} prId={numericPrId} />
          </Suspense>
        </ErrorBoundary>
        <MarkdownEditor
          onAddSingleComment={(content) => {
            handleAddComment(content);
          }}
          onUpdateComment={() => {}}
          repoId={numericRepoId}
        />
      </main>
      <aside className="w-1/6">
        <ErrorBoundary>
          <Suspense fallback={<img src={spinner} alt="Loading..." />}>
            <Reviewers repoId={numericRepoId} prId={numericPrId} />
          </Suspense>
        </ErrorBoundary>
      </aside>
    </section>
  );
}
