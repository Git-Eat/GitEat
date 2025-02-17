import { useState } from "react";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import { ko } from "date-fns/locale";
import { Replies } from "../../conversation/replies";
import defaultprofile from "../../../../assets/images/user_profile.svg";
import { MarkdownEditor } from "../../../common/markdownEditor";
import { Comment } from "../../../../api/types/Comment";
import { useLoginStore } from "../../../../store/loginStore";
import { useDeleteComment } from "../../../../api/queries/useDeleteComment";
import { useCreateReply } from "../../../../api/queries/useCreateReply";
import { useUpdateComment } from "../../../../api/queries/useUpdateComment";
import suggest from "../../../../assets/images/suggest.svg";
import commentImg from "../../../../assets/images/comment.svg";
import review from "../../../../assets/images/review.svg";
import { formatDistanceToNowStrict, parseISO } from "date-fns";
import { useParams } from "react-router-dom";
interface CommentThreadProps {
  comment: Comment;
}

export function CommentThread({ comment }: CommentThreadProps) {
  const { baseRepoId, prId } = useParams();
  const { user } = useLoginStore();
  const [isReplyEditorOpen, setIsReplyEditorOpen] = useState<
    Record<number, boolean>
  >({});
  const { mutate: deleteComment } = useDeleteComment(
    Number(baseRepoId),
    Number(prId)
  );
  const { mutate: createReply } = useCreateReply(
    Number(baseRepoId),
    Number(prId)
  );
  const { mutate: updateComment } = useUpdateComment(
    Number(baseRepoId),
    Number(prId)
  );
  const [isEditing, setIsEditing] = useState(false);
  const [editCommentId, setEditCommentId] = useState<number | null>(null);
  const [editContent, setEditContent] = useState<string>("");
  const [editCategory, setEditCategory] = useState<number>(0);
  const commentTypeImages = {
    0: { src: suggest, alt: "suggest" },
    1: { src: commentImg, alt: "comment" },
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

  function toggleReplyEditor(commentId: number) {
    setIsReplyEditorOpen((prev) => ({
      ...prev,
      [commentId]: !prev[commentId],
    }));
  }

  function handleAddReply(content: string, disId: string) {
    if (!content.trim()) return;
    createReply({ content, disId });
  }

  function handleEditComment(comment: Comment) {
    setIsEditing(true);
    setEditCommentId(comment.commentId);
    setEditCategory(comment.commentType);
    setEditContent(comment.content);
  }

  function handleSaveEdit(content: string, category: number) {
    if (editCommentId === null) return;
    updateComment({ commentId: editCommentId, content, commentType: category });
    setIsEditing(false);
    setEditCommentId(null);
    setEditContent("");
    setEditCategory(0);
  }

  function isValidCommentType(type: number): type is 0 | 1 | 2 {
    return type === 0 || type === 1 || type === 2;
  }

  return (
    <section>
      <div
        key={comment.commentId}
        className="mb-8 bg-white my-5 p-5 rounded-xl font-pretendard"
      >
        <header>
          <section className="flex justify-between items-center">
            <div className="flex items-center gap-2">
              <img
                src={comment.avatarUrl || defaultprofile}
                alt="user profile"
                className="w-9 h-9 rounded-full"
              />
              <h1 className="text-[16px] font-semibold">{comment.userName}</h1>
              {isValidCommentType(comment.commentType) ? (
                <img
                  src={commentTypeImages[comment.commentType].src}
                  alt={commentTypeImages[comment.commentType].alt}
                />
              ) : (
                <></>
              )}
            </div>

            <div>
              {Number(user.id) === comment.userId && (
                <>
                  <button
                    className="mr-2"
                    onClick={() => {
                      if (isEditing) {
                        setIsEditing(false);
                        setEditCommentId(null);
                        setEditContent("");
                        setEditCategory(0);
                      } else {
                        handleEditComment(comment);
                      }
                    }}
                  >
                    {isEditing ? "취소" : "수정"}
                  </button>
                  <button onClick={() => deleteComment(comment.commentId)}>
                    삭제
                  </button>
                </>
              )}
            </div>
          </section>
          <time className="block px-11">{displayDate(comment.createAt)}</time>
        </header>
        <article>
          <hr className="my-4" />
          <div className="px-3 max-w-none">
            <ReactMarkdown remarkPlugins={[remarkGfm]}>
              {comment.content}
            </ReactMarkdown>
            {editCommentId === comment.commentId && (
              <MarkdownEditor
                onAddSingleComment={() => {}}
                onUpdateComment={handleSaveEdit}
                initialValue={editContent}
                initialCategory={editCategory}
                isEditing={true}
                repoId={Number(baseRepoId)}
              />
            )}
          </div>
          <hr className="my-4" />
          <p className="mt-3 text-right">
            {comment.reCommentList?.length || 0}개의 답글
          </p>
          {(comment.reCommentList?.length ?? 0) > 0 && (
            <section>
              {comment.reCommentList?.map((reply) => (
                <Replies
                  key={reply.reCommentId}
                  repoId={Number(baseRepoId)}
                  prId={Number(prId)}
                  {...reply}
                  replyCreateAt={displayDate(reply.createAt)}
                />
              ))}
            </section>
          )}
        </article>
        <footer className="flex justify-end mt-2">
          <button onClick={() => toggleReplyEditor(comment.commentId)}>
            {isReplyEditorOpen[comment.commentId] ? "답글 접기" : "답글 추가"}
          </button>
        </footer>
        {isReplyEditorOpen[comment.commentId] && (
          <MarkdownEditor
            onAddSingleComment={(content) => {
              handleAddReply(content, comment.disId);
            }}
            onUpdateComment={() => {}}
            repoId={Number(baseRepoId)}
          />
        )}
      </div>
    </section>
  );
}
