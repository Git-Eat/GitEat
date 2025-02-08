import { useGetReviewer } from "../../../../api/queries/useGetReviewers";
import defaultprofile from "../../../../assets/images/user_profile.svg";
import suggest from "../../../../assets/images/suggest.svg";
import comment from "../../../../assets/images/comment.svg";
import review from "../../../../assets/images/review.svg";

import { Reviewer } from "../../../../api/types/Reviewer";

export function Reviewers() {
  const { data = [] } = useGetReviewer(888788, 32);
  const commentTypeImages = {
    1: { src: suggest, alt: "suggest" },
    2: { src: comment, alt: "comment" },
    3: { src: review, alt: "review" },
  };
  // const selectedImage = commentTypeImages[replyType];
  return (
    <section className="bg-white my-5 p-5 rounded-xl">
      <h1 className="text-[18px] font-semibold pb-5">리뷰 참여한 사람</h1>
      <ul>
        {data?.map((reviewer: Reviewer) => (
          <li key={reviewer.userId} className="mb-3">
            <img
              src={reviewer.avatarUrl || defaultprofile}
              alt={`${reviewer.userName}의 프로필 사진`}
              className="inline-block mr-2 max-w-6"
            />
            <p className="inline mr-2 text-[12px] font-semibold">
              {reviewer.userName}
            </p>
            <img
              src={commentTypeImages[reviewer.commentType].src}
              alt={commentTypeImages[reviewer.commentType].alt}
              className="inline-block max-w-12"
            />
          </li>
        ))}
      </ul>
    </section>
  );
}
