import { http, HttpResponse } from "msw";

const reviewers = [
  {
    userId: 1,
    userName: "USER-01",
    avatarUrl: null,
    commentType: 2,
  },
  {
    userId: 2,
    userName: "USER-02",
    avatarUrl: null,
    commentType: 1,
  },
  {
    userId: 3,
    userName: "USER-03",
    avatarUrl: null,
    commentType: 2,
  },
  {
    userId: 4,
    userName: "USER-04",
    avatarUrl: null,
    commentType: 3,
  },
];

const reviewersHandlers = [
  // 주소 임의 설정(추후 수정 필요)
  http.get("http://localhost:3000/undefined/pr/repoId/prId/reviewer", () => {
    return HttpResponse.json(reviewers, { status: 200 });
  }),
];

export default reviewersHandlers;
