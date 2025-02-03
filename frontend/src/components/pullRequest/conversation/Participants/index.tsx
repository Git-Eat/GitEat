import { useGetReviewer } from "../../../../api/queries/useGetReviewers";

interface Reviewer {
  user_profile: string;
  user_id: string;
  emoji: string;
}

export function Participants() {
  const { data } = useGetReviewer();
  return (
    <section className="bg-white my-5 p-5 rounded-xl">
      <h1 className="text-[18px] font-semibold pb-5">리뷰에 참여한 사람들</h1>
      <ul>
        {data?.map((reviewer: Reviewer) => (
          <li key={reviewer.user_id} className="mb-3">
            <img
              src={reviewer.user_profile}
              alt={`${reviewer.user_id}의 프로필 사진`}
              className="inline-block mr-2 max-w-6"
            />
            <p className="inline mr-2 text-[12px] font-semibold">
              {reviewer.user_id}
            </p>
            <img
              src={reviewer.emoji}
              alt={`${reviewer.user_id}의 이모지`}
              className="inline-block max-w-10"
            />
          </li>
        ))}
      </ul>
    </section>
  );
}
