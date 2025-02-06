import { Suspense } from "react";
import { Comments } from "./comments";
import { MarkdownEditor } from "../../common/markdownEditor";
import { ErrorBoundery } from "../../common/errorBoundery";
import { Reviewers } from "./reviewers";
import spinner from "../../../assets/images/spinner.svg";

export function Conversation() {
  return (
    <section className="flex gap-5">
      <main className="w-3/4">
        <ErrorBoundery>
          <Suspense fallback={<img src={spinner} alt="Loading..." />}>
            {/* repoId & prId 변경 예정 */}
            <Comments repoId={888788} prId={32} />
          </Suspense>
        </ErrorBoundery>
        <MarkdownEditor
          onAddSingleComment={() => {}}
          onStartReview={() => {}}
        />
      </main>
      <aside className="w-1/4">
        <ErrorBoundery>
          <Suspense fallback={<img src={spinner} alt="Loading..." />}>
            {/* repoId & prId 변경 예정 */}
            <Reviewers repoId={888788} prId={32} />
          </Suspense>
        </ErrorBoundery>
      </aside>
    </section>
  );
}
