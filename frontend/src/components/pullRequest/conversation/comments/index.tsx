import { useState } from "react";
import { MarkdownEditor } from "../../../common/markdownEditor";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import { useGetComments } from "../../../../api/queries/useGetComments";
import { formatDistanceToNowStrict, parseISO } from "date-fns";
import { ko } from "date-fns/locale";
import { Comment } from "../../../../api/types/Comment";
import defaultprofile from "../../../../assets/images/user_profile.svg";
import { useDeleteComment } from "../../../../api/queries/useDeleteComment";
import suggest from "../../../../assets/images/suggest.svg";
import comment from "../../../../assets/images/comment.svg";
import review from "../../../../assets/images/review.svg";
import { Replies } from "../replies";

interface CommentsProps {
  repoId: number;
  prId: number;
}

export function Comments({ repoId, prId }: CommentsProps) {
  const { data } = useGetComments(repoId, prId);
  const [isReCommentEditorOpen, setIsReCommentEditorOpen] = useState<
    Record<number, boolean>
  >({});
  const { mutate: deleteComment } = useDeleteComment(repoId, prId);
  const commentTypeImages = {
    0: { src: suggest, alt: "suggest" },
    1: { src: comment, alt: "comment" },
    2: { src: review, alt: "review" },
  };

  function displayDate(commentDate: string | null) {
    if (!commentDate) {
      return "날짜 정보가 없습니다.";
    }
    const date = parseISO(commentDate);
    return formatDistanceToNowStrict(date, {
      addSuffix: true,
      locale: ko,
    });
  }

  function toggleReCommentEditor(commentId: number) {
    setIsReCommentEditorOpen((prev) => ({
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
              <section className="flex justify-between items-center">
                <div className="flex items-center gap-2">
                  <img
                    src={comment.avatarUrl || defaultprofile}
                    alt="user profile"
                    className="w-9 h-9"
                  />
                  <h1 className="text-[16px] font-semibold">
                    {comment.userName}
                  </h1>
                  <img
                    src={commentTypeImages[comment.commentType].src}
                    alt={commentTypeImages[comment.commentType].alt}
                  />
                </div>
                <button onClick={() => deleteComment(comment.commentId)}>
                  댓글 삭제
                </button>
              </section>
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
              <hr className="my-4" />
              <p className="mt-3 text-right">
                {comment.replyList?.length || 0}개의 답글
              </p>
              {(comment.replyList?.length ?? 0) > 0 && (
                <section>
                  {comment.replyList?.map((reComment) => (
                    <Replies
                      key={reComment.reCommentId}
                      repoId={repoId}
                      prId={prId}
                      {...reComment}
                      replyCreateAt={displayDate(reComment.createAt)}
                    />
                  ))}
                </section>
              )}
            </article>
            <footer className="flex justify-end mt-2">
              <button onClick={() => toggleReCommentEditor(comment.commentId)}>
                {isReCommentEditorOpen[comment.commentId]
                  ? "답글 접기"
                  : "답글 추가"}
              </button>
            </footer>
            {isReCommentEditorOpen[comment.commentId] && (
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
