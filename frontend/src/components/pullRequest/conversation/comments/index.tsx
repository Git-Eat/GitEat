import { useState } from "react";
import { MarkdownEditor } from "../../../common/markdownEditor";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import { formatDistanceToNowStrict, parseISO } from "date-fns";
import { ko } from "date-fns/locale";
import { Comment } from "../../../../api/types/Comment";
import defaultprofile from "../../../../assets/images/user_profile.svg";
import { useDeleteComment } from "../../../../api/queries/useDeleteComment";
import suggest from "../../../../assets/images/suggest.svg";
import comment from "../../../../assets/images/comment.svg";
import review from "../../../../assets/images/review.svg";
import { Replies } from "../replies";
import { useCreateReply } from "../../../../api/queries/useCreateReply";
import { useUpdateComment } from "../../../../api/queries/useUpdateComment";
import { usePRStore } from "../../../../store/pullRequestStore";
import { CodeBlock } from "../codeBlock";
import { useLoginStore } from "../../../../store/loginStore";

interface CommentsProps {
  repoId: number;
  prId: number;
}

export function Comments({ repoId, prId }: CommentsProps) {
  const { comments } = usePRStore();
  const { user } = useLoginStore();
  const [isReplyEditorOpen, setIsReplyEditorOpen] = useState<
    Record<number, boolean>
  >({});
  const { mutate: deleteComment } = useDeleteComment(repoId, prId);
  const { mutate: createReply } = useCreateReply(repoId, prId);
  const { mutate: updateComment } = useUpdateComment(repoId, prId);
  const [isEditing, setIsEditing] = useState(false);
  const [editCommentId, setEditCommentId] = useState<number | null>(null);
  const [editContent, setEditContent] = useState<string>("");
  const [editCategory, setEditCategory] = useState<number>(0);
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
      <ul>
        {comments?.map((comment: Comment) => (
          <li
            key={comment.commentId}
            className="mb-8 bg-white my-5 p-5 rounded-xl"
          >
            {comment.position && (
              <CodeBlock
                minLine={Number(
                  Math.max(
                    comment.position.newLine ? comment.position.newLine : 0,
                    comment.position.oldLine ? comment.position.oldLine : 0
                  )
                )}
                maxLine={Number(
                  Math.max(
                    comment.position.newLine ? comment.position.newLine : 0,
                    comment.position.oldLine ? comment.position.oldLine : 0
                  )
                )}
                newPath={comment.position.newPath}
                oldPath={comment.position.oldPath}
                fileId={comment.fileId}
              />
            )}
            <header>
              <section className="flex justify-between items-center">
                <div className="flex items-center gap-2">
                  <img
                    src={comment.avatarUrl || defaultprofile}
                    alt="user profile"
                    className="w-9 h-9 rounded-full"
                  />
                  <h1 className="text-[16px] font-semibold">
                    {comment.userName}
                  </h1>
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
                {editCommentId === comment.commentId && (
                  <MarkdownEditor
                    onAddSingleComment={() => {}}
                    onUpdateComment={handleSaveEdit}
                    initialValue={editContent}
                    initialCategory={editCategory}
                    isEditing={true}
                    repoId={repoId}
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
                      repoId={repoId}
                      prId={prId}
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
                onAddSingleComment={(content) => {
                  handleAddReply(content, comment.disId);
                }}
                onUpdateComment={() => {}}
                repoId={repoId}
              />
            )}
          </li>
        ))}
      </ul>
    </section>
  );
}
