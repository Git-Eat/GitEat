import { Comments } from "./comments";
import { MarkdownEditor } from "../../common/markdownEditor";
import { Reviewers } from "./reviewers";
import { CodeBlock } from "./codeBlock";
export const oldCode: string = `
function example() {
  console.log("Line 1: Start function");
  console.log("Line 2: Initialization");
  console.log("Line 3: Setup complete");
  console.log("Line 4: Performing operation A");
  console.log("Line 5: Operation A complete");
  console.log("Line 6: Performing operation B");
  console.log("Line 7: Operation B complete");
  console.log("Line 8: Finalizing process");
  console.log("Line 9: Process finalized");
  console.log("Line 10: Returning result");
  console.log("Line 11: End function");
}
`;
export const newCode: string = `
function example() {
  console.log("Line 1: Start function");
  console.log("Line 2: Initialization");
  console.log("Line 3: Setup complete");
  console.log("Line 4: Performing operation A (modified)");
  console.log("Line 5: Operation A complete");
  console.log("Line 6: Performing operation B");
  console.log("Line 7: Operation B complete (modified)");
  console.log("Line 8: Finalizing process");
  console.log("Line 9: Process finalized");
  console.log("Line 10: Returning final result");
  console.log("Line 11: End function");
}
`;
export function Conversation() {
  return (
    <>
      <section className="flex gap-5">
        <main className="w-3/4">
          <CodeBlock
            oldCode={oldCode}
            newCode={newCode}
            minLine={8}
            maxLine={10}
          />
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
