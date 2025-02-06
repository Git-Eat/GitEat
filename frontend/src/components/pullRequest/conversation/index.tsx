import { Suspense } from "react";
import { Comments } from "./comments";
import { MarkdownEditor } from "../../common/markdownEditor";
import { ErrorBoundary } from "../../common/errorBoundery";
import { Reviewers } from "./reviewers";
import spinner from "../../../assets/images/spinner.svg";

export function Conversation() {
  return (
    <section className="flex gap-5">
      <main className="w-3/4">
        <ErrorBoundary>
          <Suspense fallback={<img src={spinner} alt="Loading..." />}>
            <Comments />
          </Suspense>
        </ErrorBoundary>
        <MarkdownEditor
          onAddSingleComment={() => {}}
          onStartReview={() => {}}
        />
      </main>
      <aside className="w-1/4">
        <Reviewers />
      </aside>
    </section>
  );
}
