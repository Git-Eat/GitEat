import { useState } from "react";
// import { FileMarkDownEditor } from "../fileMarkDownEditor";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import { getParsedDate } from "../../../../utils/getParsedDate";
import { Reply } from "../../../../api/types/Reply";
import { Replies } from "../../conversation/replies";
import defaultprofile from "../../../../assets/images/user_profile.svg";
type Comment = {
  commentId: number;
  prId: number;
  repoId: number;
  userId: number;
  userName: string;
  avatarUrl: string;
  disId: string;
  content: string;
  commentType: number;
  createAt: string;
  position: {
    baseSha: null | string;
    startSha: null | string;
    headSha: null | string;
    oldPath: string;
    newPath: string;
    positionType: string;
    newLine: number;
    oldLine: number;
    newStartLine: number;
    newEndLine: number;
    oldStartLine: number;
    oldEndLine: number;
    lineRange: {
      start: {
        lineCode: string;
      };
      end: {
        lineCode: string;
      };
    };
  } | null;
  reCommentList: Reply[];
};
interface CommentThreadProps {
  comment: Comment;
}

export function CommentThread({ comment }: CommentThreadProps) {
  console.log(comment.reCommentList);
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
    <div key={comment.commentId} className="mb-8 bg-white my-5 p-5 rounded-xl">
      <header>
        <img
          src={comment.avatarUrl || defaultprofile}
          alt="user profile"
          className="inline-block w-9 h-9 mr-2"
        />
        <h1 className="inline text-[16px] font-semibold">{comment.userName}</h1>
        <time className="block px-11">{getParsedDate(comment.createAt)}</time>
      </header>
      <article>
        <hr className="my-4" />
        <div className="px-3 prose prose-lg max-w-none">
          <ReactMarkdown remarkPlugins={[remarkGfm]}>
            {comment.content}
          </ReactMarkdown>
        </div>
        <hr className="my-4" />
        <p className="mt-3 text-right">
          {comment.reCommentList?.length}개의 답글
        </p>
        <section>
          {comment.reCommentList?.map((reply) => (
            <Replies
              key={reply.reCommentId}
              repoId={comment.repoId}
              prId={comment.prId}
              {...reply}
              replyCreateAt={getParsedDate(reply.createAt)}
            />
          ))}
        </section>
      </article>
      <footer className="flex justify-end mt-2">
        <button onClick={() => toggleReplyEditor(comment.commentId)}>
          {isReplyEditorOpen[comment.commentId] ? "답글 접기" : "답글 추가"}
        </button>
      </footer>
      {/* {isReplyEditorOpen[comment.commentId] && (
        <FileMarkDownEditor
          onClose={() => toggleReplyEditor(comment.commentId)}
          newStartLine={comment.position?.newLine || 0}
          oldStartLine={comment.position?.newLine || 0}
          newEndLine={comment.position?.newLine || 0}
          oldEndLine={comment.position?.newLine || 0}
          fil
        />
      )} */}
    </div>
  );
}
