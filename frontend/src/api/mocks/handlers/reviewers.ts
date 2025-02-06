import { http, HttpResponse } from "msw";

const reviewers = [
  {
    userId: 1,
    userName: "송용인",
    avatarUrl: null,
    commentType: 1,
  },
  {
    userId: 2,
    userName: "조창훈",
    avatarUrl: null,
    commentType: 0,
  },
  {
    userId: 3,
    userName: "신지혜",
    avatarUrl: null,
    commentType: 0,
  },
  {
    userId: 5,
    userName: "이다영",
    avatarUrl: null,
    commentType: 2,
  },
  {
    userId: 6,
    userName: "이해루",
    avatarUrl: null,
    commentType: 2,
  },
  {
    userId: 4,
    userName: "최이화",
    avatarUrl: null,
    commentType: 1,
  },
  {
    userId: 4,
    userName: "최이화",
    avatarUrl: null,
    commentType: 0,
  },
  {
    userId: 4,
    userName: "최이화",
    avatarUrl: null,
    commentType: 2,
  },
];

const reviewersHandlers = [
  // 주소 임의 설정(추후 수정 필요)
  http.get("*/pr/:repoId/:prId/reviewer", () => {
    return HttpResponse.json(reviewers, { status: 200 });
  }),
];

export default reviewersHandlers;
