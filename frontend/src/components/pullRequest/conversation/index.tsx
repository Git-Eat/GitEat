import { Comments } from "./comments";
import { MarkdownEditor } from "../../common/markdownEditor";
import { ParticipantList } from "./participantList";

export function Conversation() {
  return (
    <section className="flex gap-5">
      <main className="w-3/4">
        <Comments />
        <MarkdownEditor />
      </main>
      <aside className="w-1/4">
        <ParticipantList />
      </aside>
    </section>
  );
}
