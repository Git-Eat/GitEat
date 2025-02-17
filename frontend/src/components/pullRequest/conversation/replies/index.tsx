import { useState } from "react";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import { Reply } from "../../../../api/types/Reply";
import { useDeleteReply } from "../../../../api/queries/useDeleteReply";
import suggest from "../../../../assets/images/suggest.svg";
import comment from "../../../../assets/images/comment.svg";
import review from "../../../../assets/images/review.svg";
import defaultprofile from "../../../../assets/images/user_profile.svg";
import { MarkdownEditor } from "../../../common/markdownEditor";
import { useUpdateReply } from "../../../../api/queries/useUpdateReply";
import { useLoginStore } from "../../../../store/loginStore";

interface ReCommentProps extends Reply {
  repoId: number;
  prId: number;
  replyCreateAt: string;
}

export function Replies({
  repoId,
  prId,
  reCommentId,
  userId,
  userName,
  avatarUrl,
  discussionId,
  content,
  reCommentType,
  imageName,
  createAt,
  replyCreateAt,
}: ReCommentProps) {
  const reply = {
    reCommentId,
    userId,
    userName,
    avatarUrl,
    discussionId,
    content,
    reCommentType,
    imageName,
    createAt,
  };
  const replyTypeImages = {
    0: { src: suggest, alt: "suggest" },
    1: { src: comment, alt: "comment" },
    2: { src: review, alt: "review" },
  };
  const { mutate: deleteReComment } = useDeleteReply(repoId, prId);
  const { mutate: updateReply } = useUpdateReply(repoId, prId);
  const [isEditing, setIsEditing] = useState(false);
  const [editReplyId, setEditReplyId] = useState<number | null>(null);
  const [editContent, setEditContent] = useState<string>("");
  const [editCategory, setEditCategory] = useState<number>(0);
  const { user } = useLoginStore();

  function handleEditReply(reply: Reply) {
    setIsEditing(true);
    setEditReplyId(reply.reCommentId);
    setEditCategory(reply.reCommentType);
    setEditContent(reply.content);
  }

  function handleSaveEdit(content: string, category: number) {
    if (editReplyId === null) return;
    updateReply({ reCommentId: editReplyId, content, replyType: category });
    setIsEditing(false);
    setEditReplyId(null);
    setEditContent("");
    setEditCategory(0);
  }

  function isValidReplyType(type: number): type is 0 | 1 | 2 {
    return type === 0 || type === 1 || type === 2;
  }

  return (
    <section className="bg-stone-50 my-3 p-5 rounded-xl">
      <header className="flex justify-between items-center">
        <div className="flex items-center gap-2">
          <img
            src={avatarUrl || defaultprofile}
            alt="user profile"
            className="w-9 h-9 rounded-full"
          />
          <h1 className="text-[16px] font-semibold">{userName}</h1>
          {isValidReplyType(reCommentType) ? (
            <img
              src={replyTypeImages[reCommentType].src}
              alt={replyTypeImages[reCommentType].alt}
            />
          ) : (
            <></>
          )}
          <time className="mr-2">{replyCreateAt}</time>
        </div>
        <div>
          {Number(user.id) === reply.userId && (
            <>
              <button
                onClick={() => {
                  if (isEditing) {
                    setIsEditing(false);
                    setEditReplyId(null);
                    setEditContent("");
                    setEditCategory(0);
                  } else {
                    handleEditReply(reply);
                  }
                }}
                className="mr-2"
              >
                {isEditing ? "취소" : "수정"}
              </button>
              <button onClick={() => deleteReComment(reCommentId)}>삭제</button>
            </>
          )}
        </div>
      </header>
      <section className="px-10 py-3">
        <ReactMarkdown remarkPlugins={[remarkGfm]}>{content}</ReactMarkdown>
      </section>
      {editReplyId === reCommentId && (
        <MarkdownEditor
          onAddSingleComment={() => {}}
          onUpdateComment={handleSaveEdit}
          initialValue={editContent}
          initialCategory={editCategory}
          isEditing={true}
          repoId={repoId}
        />
      )}
    </section>
  );
}
