import { Comments } from "./comments";
import { MarkdownEditor } from "../../common/markdownEditor";
import { Reviewers } from "./reviewers";

export function Conversation() {
  return (
    <>
      <section className="flex gap-5">
        <main className="w-3/4">
          <Comments />
          <MarkdownEditor
            onAddSingleComment={() => {}}
            onStartReview={() => {}}
          />
        </main>
        <aside className="w-1/4">
          <Reviewers />
        </aside>
      </section>
    </>
  );
}
