import { http, HttpResponse } from "msw";
import user_profile_2 from "../../../assets/images/user_profile_2.svg";
import user_profile_3 from "../../../assets/images/user_profile_3.svg";
import user_profile_4 from "../../../assets/images/user_profile_4.svg";
import suggest from "../../../assets/images/suggest.svg";
import comment from "../../../assets/images/comment.svg";
import review from "../../../assets/images/review.svg";

const reviewers = [
  {
    user_profile: user_profile_2,
    user_id: "Lilyoung",
    emoji: suggest,
  },
  {
    user_profile: user_profile_3,
    user_id: "singwisdom",
    emoji: comment,
  },
  {
    user_profile: user_profile_4,
    user_id: "ih_o8",
    emoji: review,
  },
];

const reviewersHandlers = [
  // 주소 임의 설정(추후 수정 필요)
  http.get("http://localhost:3000/undefined/pr/repoId/prId/reviewer", () => {
    return HttpResponse.json(reviewers, { status: 200 });
  }),
];

export default reviewersHandlers;
