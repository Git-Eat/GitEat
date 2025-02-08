import { useState } from "react";
import { FileMarkDownEditor } from "../fileMarkDownEditor";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import { getParsedDate } from "../../../../utils/getParsedDate";
import { Reply } from "../../../../api/types/Reply";
import { Replies } from "../../conversation/replies";
import defaultprofile from "../../../../assets/images/user_profile.svg";
type Comment = {
  body: {
    commentId: number;
    prId: number;
    repoId: number;
    userName: string;
    avatarUrl: string;
    disId: string;
    content: string;
    commentType: number;
    imageName: string;
    createAt: string;
    replyList: Reply[];
  };
  position: {
    base_sha: string;
    start_sha: string;
    head_sha: string;
    old_path: string;
    new_path: string;
    position_type: string;
    new_line?: number;
    old_line?: number;
    line_range: {
      start: {
        line_code: string;
      };
      end: {
        line_code: string;
      };
    };
  };
};
interface CommentThreadProps {
  comment: Comment;
}

export function CommentThread({ comment }: CommentThreadProps) {
  console.log(comment.body.replyList);
  const [isReplyEditorOpen, setIsReplyEditorOpen] = useState<
    Record<number, boolean>
  >({});
  function toggleReplyEditor(commentId: number) {
    setIsReplyEditorOpen((prev) => ({
      ...prev,
      [commentId]: !prev[commentId],
    }));
  }
  return (
    <div
      key={comment.body.commentId}
      className="mb-8 bg-white my-5 p-5 rounded-xl"
    >
      <header>
        <img
          src={comment.body.avatarUrl || defaultprofile}
          alt="user profile"
          className="inline-block w-9 h-9 mr-2"
        />
        <h1 className="inline text-[16px] font-semibold">
          {comment.body.userName}
        </h1>
        <time className="block px-11">
          {getParsedDate(comment.body.createAt)}
        </time>
      </header>
      <article>
        <hr className="my-4" />
        <div className="px-3 prose prose-lg max-w-none">
          <ReactMarkdown remarkPlugins={[remarkGfm]}>
            {comment.body.content}
          </ReactMarkdown>
        </div>
        <hr className="my-4" />
        <p className="mt-3 text-right">
          {comment.body.replyList?.length}개의 답글
        </p>
        <section>
          {comment.body.replyList?.map((reply) => (
            <Replies
              key={reply.reCommentId}
              {...reply}
              replyCreateAt={getParsedDate(reply.createAt)}
            />
          ))}
        </section>
      </article>
      <footer className="flex justify-end mt-2">
        <button onClick={() => toggleReplyEditor(comment.body.commentId)}>
          {isReplyEditorOpen[comment.body.commentId]
            ? "답글 접기"
            : "답글 추가"}
        </button>
      </footer>
      {isReplyEditorOpen[comment.body.commentId] && (
        <FileMarkDownEditor
          addReview={() => {}}
          submitComment={() => {}}
          onClose={() => toggleReplyEditor(comment.body.commentId)}
          startLine={comment.position.new_line || 0}
          endLine={comment.position.new_line || 0}
        />
      )}
    </div>
  );
}
