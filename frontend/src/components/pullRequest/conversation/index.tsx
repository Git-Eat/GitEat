import { MarkdownEditor } from "../../common/markkdownEditor";
import { Comment } from "./comment";
import { ParticipantList } from "./participantList";

export function Conversation() {
  return (
    <div className="flex gap-5">
      <div className="w-3/4">
        <Comment />
        <MarkdownEditor />
      </div>
      <div className="w-1/4">
        <ParticipantList />
      </div>
    </div>
  );
}
