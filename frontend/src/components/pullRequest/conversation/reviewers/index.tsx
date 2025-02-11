import { useGetReviewer } from "../../../../api/queries/useGetReviewers";
import { Reviewer } from "../../../../api/types/Reviewer";
import defaultprofile from "../../../../assets/images/user_profile.svg";
import suggest from "../../../../assets/images/suggest.svg";
import comment from "../../../../assets/images/comment.svg";
import review from "../../../../assets/images/review.svg";

interface ReviewersProps {
  repoId: number;
  prId: number;
}

export function Reviewers({ repoId, prId }: ReviewersProps) {
  const { data } = useGetReviewer(repoId, prId);

  const commentTypeImages: Record<number, { src: string; alt: string }> = {
    0: { src: suggest, alt: "suggest" },
    1: { src: comment, alt: "comment" },
    2: { src: review, alt: "review" },
  };

  const reviewerMap: Map<number, Reviewer & { commentTypes: Set<number> }> =
    new Map();

  (Array.isArray(data) ? data : []).forEach((reviewer: Reviewer) => {
    const key = reviewer.userId;
    if (!reviewerMap.has(key)) {
      reviewerMap.set(key, { ...reviewer, commentTypes: new Set() });
    }
    reviewerMap.get(key)?.commentTypes.add(reviewer.commentType);
  });

  const filteredReviewer = Array.from(reviewerMap.values());

  return (
    <section className="bg-white my-5 p-5 rounded-xl">
      <h1 className="text-[18px] font-semibold pb-5">리뷰 참여한 사람</h1>
      <ul>
        {filteredReviewer?.map((reviewer) => (
          <li key={reviewer.userId} className="mb-3">
            <img
              src={reviewer.avatarUrl || defaultprofile}
              alt="profile Image"
              className="inline-block mr-2 max-w-6 rounded-full"
            />
            <p className="inline mr-2 text-[12px] font-semibold">
              {reviewer.userName}
            </p>
            {Array.from(reviewer.commentTypes)
              .sort((a, b) => a - b)
              .map((reviewType: number) => (
                <img
                  key={reviewType}
                  src={commentTypeImages[reviewType].src}
                  alt={commentTypeImages[reviewType].alt}
                  className="inline-block max-w-12 mr-2"
                />
              ))}
          </li>
        ))}
      </ul>
    </section>
  );
}
