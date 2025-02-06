import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import { Reply } from "../../../../api/types/Reply";
import suggest from "../../../../assets/images/suggest.svg";
import comment from "../../../../assets/images/comment.svg";
import review from "../../../../assets/images/review.svg";
import defaultprofile from "../../../../assets/images/user_profile.svg";
import { useDeleteReComment } from "../../../../api/queries/useDeleteReComment";

interface ReCommentProps extends Reply {
  reCommentCreateAt: string;
  repoId: number;
  prId: number;
}

export function ReComments({
  reCommentId,
  userName,
  avatarUrl,
  content,
  replyType,
  reCommentCreateAt,
  repoId,
  prId,
}: ReCommentProps) {
  const reCommentTypeImages = {
    0: { src: suggest, alt: "suggest" },
    1: { src: comment, alt: "comment" },
    2: { src: review, alt: "review" },
  };
  const selectedImage = reCommentTypeImages[replyType];
  const { mutate: deleteReComment } = useDeleteReComment(repoId, prId);

  return (
    <section className="bg-gray-100 my-3 p-5 rounded-xl">
      <header className="flex justify-between items-center">
        <div className="flex items-center gap-2">
          <img
            src={avatarUrl || defaultprofile}
            alt="user profile"
            className="w-9 h-9 rounded"
          />
          <h1 className="text-[16px] font-semibold">{userName}</h1>
          <img src={selectedImage.src} alt={selectedImage.alt} />
          <time className="mr-2">{reCommentCreateAt}</time>
        </div>
        <button onClick={() => deleteReComment(reCommentId)}>답글 삭제</button>
      </header>
      <section className="px-10 py-3">
        <ReactMarkdown remarkPlugins={[remarkGfm]}>{content}</ReactMarkdown>
      </section>
    </section>
  );
}
