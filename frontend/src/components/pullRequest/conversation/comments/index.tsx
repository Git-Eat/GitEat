import { useState } from "react";
import { MarkdownEditor } from "../../../common/markdownEditor";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import { useGetComments } from "../../../../api/queries/useGetComments";
import { formatDistanceToNowStrict, parseISO } from "date-fns";
import { ko } from "date-fns/locale";
import { Comment } from "../../../../api/types/Comment";
import { Replies } from "../replies";
import defaultprofile from "../../../../assets/images/user_profile.svg";
import { useDeleteComment } from "../../../../api/queries/useDeleteComment";

interface CommentsProps {
  repoId: number;
  prId: number;
}

export function Comments({ repoId, prId }: CommentsProps) {
  const { data } = useGetComments(repoId, prId);
  const [isReplyEditorOpen, setIsReplyEditorOpen] = useState<
    Record<number, boolean>
  >({});
  const { mutate: deleteComment } = useDeleteComment(repoId, prId);

  function displayDate(commentDate: string) {
    const date = parseISO(commentDate);
    return formatDistanceToNowStrict(date, {
      addSuffix: true,
      locale: ko,
    });
  }

  function toggleReplyEditor(commentId: number) {
    setIsReplyEditorOpen((prev) => ({
      ...prev,
      [commentId]: !prev[commentId],
    }));
  }

  return (
    <section>
      <ul>
        {data?.map((comment: Comment) => (
          <li
            key={comment.commentId}
            className="mb-8 bg-white my-5 p-5 rounded-xl"
          >
            <header>
              <img
                src={comment.avatarUrl || defaultprofile}
                alt="user profile"
                className="inline-block w-9 h-9 mr-2"
              />
              <h1 className="inline text-[16px] font-semibold">
                {comment.userName}
              </h1>
              <time className="block px-11">
                {displayDate(comment.createAt)}
              </time>
            </header>
            <article>
              <hr className="my-4" />
              <div className="px-3 prose prose-lg max-w-none">
                <ReactMarkdown remarkPlugins={[remarkGfm]}>
                  {comment.content}
                </ReactMarkdown>
              </div>
              <button onClick={() => deleteComment(comment.commentId)}>
                댓글 삭제
              </button>
              <hr className="my-4" />
              <p className="mt-3 text-right">
                {comment.replyList.length}개의 답글
              </p>
              {comment.replyList.length > 0 && (
                <section>
                  {comment.replyList?.map((reply) => (
                    <Replies
                      key={reply.reCommentId}
                      {...reply}
                      replyCreateAt={displayDate(reply.createAt)}
                    />
                  ))}
                </section>
              )}
            </article>
            <footer className="flex justify-end mt-2">
              <button onClick={() => toggleReplyEditor(comment.commentId)}>
                {isReplyEditorOpen[comment.commentId]
                  ? "답글 접기"
                  : "답글 추가"}
              </button>
            </footer>
            {isReplyEditorOpen[comment.commentId] && (
              <MarkdownEditor
                onAddSingleComment={() => {}}
                onStartReview={() => {}}
              />
            )}
          </li>
        ))}
      </ul>
    </section>
  );
}
