import { useGetReviewer } from "../../../../api/queries/useGetReviewers";
import { Reviewer } from "../../../../api/types/Reviewer";
import defaultprofile from "../../../../assets/images/user_profile.svg";
import suggest from "../../../../assets/images/suggest.svg";
import comment from "../../../../assets/images/comment.svg";
import review from "../../../../assets/images/review.svg";
import sadCat from "../../../../assets/images/sad_cat.svg";
import { useMemo } from "react";

interface ReviewersProps {
  repoId: number;
  prId: number;
}

interface FilteredReviewer {
  userId: number;
  name: string;
  avatarUrl: string;
  commentTypes: number[];
  // 만약 contentType도 처리해야 한다면 아래와 같이 추가할 수 있습니다.
  // contentTypes: number[];
}

export function Reviewers({ repoId, prId }: ReviewersProps) {
  const { data } = useGetReviewer(repoId, prId);

  const commentTypeImages: Record<number, { src: string; alt: string }> = {
    0: { src: suggest, alt: "suggest" },
    1: { src: comment, alt: "comment" },
    2: { src: review, alt: "review" },
  };

  const filteredReviewers = useMemo(() => {
    if (!data || !data.reviewer) return [];

    // userId를 기준으로 리뷰어 정보를 관리하는 Map
    const reviewerMap = new Map<number, FilteredReviewer>();

    data.reviewer.forEach((reviewer: Reviewer) => {
      const existing = reviewerMap.get(reviewer.userId);
      if (existing) {
        // 중복 체크: 기존 배열에 없는 경우에만 추가
        if (!existing.commentTypes.includes(reviewer.commentType)) {
          existing.commentTypes.push(reviewer.commentType);
        }
        // 만약 contentType도 합쳐야 한다면 아래처럼 진행합니다.
        // if (!existing.contentTypes.includes(reviewer.contentType)) {
        //   existing.contentTypes.push(reviewer.contentType);
        // }
      } else {
        reviewerMap.set(reviewer.userId, {
          userId: reviewer.userId,
          name: reviewer.name,
          avatarUrl: reviewer.avatarUrl,
          commentTypes: [reviewer.commentType],
          // contentTypes: [reviewer.contentType],
        });
      }
    });
    return Array.from(reviewerMap.values());
  }, [data]);

  return (
    <section className="bg-white my-5 p-5 rounded-xl">
      <h1 className="text-[18px] font-semibold pb-5">리뷰 참여한 사람</h1>
      {filteredReviewers.length === 0 ? (
        <div className="w-full flex justify-center items-center">
          <img src={sadCat} alt="" className="opacity-50" />
        </div>
      ) : (
        <ul>
          {filteredReviewers.map((reviewer) => (
            <li key={reviewer.userId} className="mb-3">
              <img
                src={reviewer.avatarUrl || defaultprofile}
                alt="profile Image"
                className="inline-block mr-2 max-w-6 rounded-full"
              />
              <p className="inline mr-2 text-[12px] font-semibold">
                {reviewer.name}
              </p>
              {reviewer.commentTypes.map((reviewType, index) => (
                <img
                  key={index}
                  src={commentTypeImages[reviewType].src}
                  alt={commentTypeImages[reviewType].alt}
                  className="inline-block max-w-12 mr-2"
                />
              ))}
            </li>
          ))}
        </ul>
      )}
    </section>
  );
}
