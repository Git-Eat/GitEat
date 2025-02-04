import { Reply } from "../../../../api/types/Reply";
import suggest from "../../../../assets/images/suggest.svg";
import comment from "../../../../assets/images/comment.svg";
import review from "../../../../assets/images/review.svg";
import defaultprofile from "../../../../assets/images/user_profile.svg";

interface ReplyProps extends Reply {
  replyCreateAt: string;
}

export function Replies({
  userName,
  avatarUrl,
  content,
  replyType,
  replyCreateAt,
}: ReplyProps) {
  const commentTypeImages = {
    1: { src: suggest, alt: "suggest" },
    2: { src: comment, alt: "comment" },
    3: { src: review, alt: "review" },
  };
  const selectedImage = commentTypeImages[replyType];

  return (
    <section className="bg-gray-100 my-3 p-5 rounded-xl">
      <header>
        <img
          src={avatarUrl || defaultprofile}
          alt="user profile"
          className="inline-block w-9 h-9 mr-2"
        />
        <h1 className="inline text-[16px] font-semibold mr-2">{userName}</h1>
        <img
          src={selectedImage.src}
          alt={selectedImage.alt}
          className="inline-block mr-2"
        />

        <time>{replyCreateAt}</time>
      </header>
      <p className="px-10 py-3">{content}</p>
    </section>
  );
}
