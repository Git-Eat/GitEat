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

interface ReCommentProps extends Reply {
  replyCreateAt: string;
  repoId: number;
  prId: number;
}

export function Replies({
  reCommentId,
  userId,
  userName,
  avatarUrl,
  disId,
  content,
  replyType,
  imageName,
  createAt,
  replyCreateAt,
  repoId,
  prId,
}: ReCommentProps) {
  const reply: Reply = {
    reCommentId,
    userId,
    userName,
    avatarUrl,
    disId,
    content,
    replyType,
    imageName,
    createAt,
  };
  const replyTypeImages = {
    0: { src: suggest, alt: "suggest" },
    1: { src: comment, alt: "comment" },
    2: { src: review, alt: "review" },
  };
  const selectedImage = replyTypeImages[replyType];
  const { mutate: deleteReComment } = useDeleteReply(repoId, prId);
  const { mutate: updateReply } = useUpdateReply(repoId, prId);
  const [isEditing, setIsEditing] = useState(false);
  const [editReplyId, setEditReplyId] = useState<number | null>(null);
  const [editContent, setEditContent] = useState<string>("");
  const [editCategory, setEditCategory] = useState<0 | 1 | 2>(0);

  function handleEditReply(reply: Reply) {
    setIsEditing(true);
    setEditReplyId(reply.reCommentId);
    setEditCategory(reply.replyType);
    setEditContent(reply.content);
  }

  function handleSaveEdit(content: string, category: 0 | 1 | 2) {
    if (editReplyId === null) return;
    updateReply({ reCommentId: editReplyId, content, replyType: category });
    setIsEditing(false);
    setEditReplyId(null);
    setEditContent("");
    setEditCategory(0);
  }

  return (
    <section className="bg-gray-100 my-3 p-5 rounded-xl">
      <header className="flex justify-between items-center">
        <div className="flex items-center gap-2">
          <img
            src={avatarUrl || defaultprofile}
            alt="user profile"
            className="w-9 h-9 rounded-full"
          />
          <h1 className="text-[16px] font-semibold">{userName}</h1>
          <img src={selectedImage.src} alt={selectedImage.alt} />
          <time className="mr-2">{replyCreateAt}</time>
        </div>
        <div>
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
            {isEditing ? "수정 취소" : "답글 수정"}
          </button>
          <button onClick={() => deleteReComment(reCommentId)}>
            답글 삭제
          </button>
        </div>
      </header>
      <section className="px-10 py-3">
        <ReactMarkdown remarkPlugins={[remarkGfm]}>{content}</ReactMarkdown>
      </section>
      {editReplyId === reCommentId && (
        <MarkdownEditor
          onAddSingleComment={() => {}}
          onStartReview={() => {}}
          onUpdateComment={handleSaveEdit}
          initialValue={editContent}
          initialCategory={editCategory}
          isEditing={true}
        />
      )}
    </section>
  );
}
