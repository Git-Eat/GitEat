import { http, HttpResponse } from "msw";
const API_BASE = import.meta.env.VITE_API_BASE;
const dashBoardHandler = [
  http.get(`${API_BASE}/statistics/repo/:repoId/commit`, async () => {
    // 3초(3000ms) 지연
    console.log("add");
    await new Promise((resolve) => setTimeout(resolve, 3000));
    return HttpResponse.json({
      total_commit: 999999,
    });
  }),
  http.get(`${API_BASE}/statistics/repo/:repoId/participants`, async () => {
    // 3초(3000ms) 지연
    console.log("add");
    await new Promise((resolve) => setTimeout(resolve, 3000));
    return HttpResponse.json({
      participants: [
        {
          userId: 22158,
          name: "이하영",
          userName: "lhy23456",
          avatarUrl:
            "https://secure.gravatar.com/avatar/160d57d4c22cf54206e04621aa12d9be6798c9076eaccf4536a4b85fbaa644e0?s=80&d=identicon",
        },
        {
          userId: 22219,
          name: "이해루",
          userName: "gofn080776",
          avatarUrl:
            "https://secure.gravatar.com/avatar/64d76aebe92226f9ea325dc5d35a44327d62594998d76d6905a47b6a0f61ae92?s=80&d=identicon",
        },
      ],
    });
  }),
  http.get(`${API_BASE}/statistics/repo/:repoId/pr`, async () => {
    // 3초(3000ms) 지연
    console.log("add");
    await new Promise((resolve) => setTimeout(resolve, 3000));
    return HttpResponse.json({
      totalMergeRequest: 113,
      userList: [
        {
          userId: 22158,
          name: "이하영",
          mergeRequestCount: 27,
          userName: "lhy23456",
          avatarUrl:
            "https://secure.gravatar.com/avatar/160d57d4c22cf54206e04621aa12d9be6798c9076eaccf4536a4b85fbaa644e0?s=80&d=identicon",
        },
        {
          userId: 22219,
          name: "이해루",
          mergeRequestCount: 18,
          userName: "gofn080776",
          avatarUrl:
            "https://secure.gravatar.com/avatar/64d76aebe92226f9ea325dc5d35a44327d62594998d76d6905a47b6a0f61ae92?s=80&d=identicon",
        },
      ],
    });
  }),
];

export default dashBoardHandler;
