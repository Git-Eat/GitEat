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
  http.get(`${API_BASE}/statistics/repo/:repoId/comment`, async () => {
    // 3초(3000ms) 지연
    console.log("add");
    await new Promise((resolve) => setTimeout(resolve, 3000));
    return HttpResponse.json({
      totalComment: 7,
      userList: [
        {
          userId: 22158,
          commentCount: 5,
          name: "이하영",
          userName: "lhy23456",
          avatarUrl:
            "https://secure.gravatar.com/avatar/160d57d4c22cf54206e04621aa12d9be6798c9076eaccf4536a4b85fbaa644e0?s=80&d=identicon",
        },
        {
          userId: 22219,
          commentCount: 2,
          name: "이해루",
          userName: "gofn080776",
          avatarUrl:
            "https://secure.gravatar.com/avatar/64d76aebe92226f9ea325dc5d35a44327d62594998d76d6905a47b6a0f61ae92?s=80&d=identicon",
        },
      ],
    });
  }),
  http.get(`${API_BASE}/statistics/repo/:repoId/contributors`, async () => {
    // 3초(3000ms) 지연
    console.log("add");
    await new Promise((resolve) => setTimeout(resolve, 3000));
    return HttpResponse.json({
      contributors: [
        {
          userId: 21650,
          name: "이다영",
          userName: "daosp1023",
          avatarUrl:
            "https://lab.ssafy.com/uploads/-/system/user/avatar/21650/avatar.png",
          totalCommit: 44,
          totalMergeRequest: 11,
          totalComment: 0,
          weeklyInfo: [
            {
              week: 202507,
              mergeRequestCount: 2,
              commitCount: 6,
              commentCount: 0,
            },
            {
              week: 202506,
              mergeRequestCount: 6,
              commitCount: 23,
              commentCount: 0,
            },
            {
              week: 202505,
              mergeRequestCount: 0,
              commitCount: 6,
              commentCount: 0,
            },
            {
              week: 202504,
              mergeRequestCount: 1,
              commitCount: 6,
              commentCount: 0,
            },
            {
              week: 202503,
              mergeRequestCount: 2,
              commitCount: 3,
              commentCount: 0,
            },
            {
              week: 202502,
              mergeRequestCount: 0,
              commitCount: 0,
              commentCount: 0,
            },
          ],
        },
        {
          userId: 21879,
          name: "최이화",
          userName: "ihwa220",
          avatarUrl:
            "https://secure.gravatar.com/avatar/c1cfcd69bd0f4f338373504e930553ebbef699aa0fb6c3c7ddf2cec52d94e76c?s=80&d=identicon",
          totalCommit: 92,
          totalMergeRequest: 10,
          totalComment: 12,
          weeklyInfo: [
            {
              week: 202507,
              mergeRequestCount: 3,
              commitCount: 12,
              commentCount: 0,
            },
            {
              week: 202506,
              mergeRequestCount: 4,
              commitCount: 51,
              commentCount: 2,
            },
            {
              week: 202505,
              mergeRequestCount: 1,
              commitCount: 17,
              commentCount: 5,
            },
            {
              week: 202504,
              mergeRequestCount: 1,
              commitCount: 11,
              commentCount: 5,
            },
            {
              week: 202503,
              mergeRequestCount: 1,
              commitCount: 1,
              commentCount: 0,
            },
            {
              week: 202502,
              mergeRequestCount: 0,
              commitCount: 0,
              commentCount: 0,
            },
          ],
        },
        {
          userId: 22147,
          name: "신지혜",
          userName: "0903jihyie",
          avatarUrl:
            "https://secure.gravatar.com/avatar/5a7047c33f01f87edfef9789e87cc5e3604ac367b6589f9e8a43bf7465ab8e24?s=80&d=identicon",
          totalCommit: 49,
          totalMergeRequest: 18,
          totalComment: 12,
          weeklyInfo: [
            {
              week: 202507,
              mergeRequestCount: 5,
              commitCount: 8,
              commentCount: 1,
            },
            {
              week: 202506,
              mergeRequestCount: 9,
              commitCount: 19,
              commentCount: 1,
            },
            {
              week: 202505,
              mergeRequestCount: 3,
              commitCount: 20,
              commentCount: 5,
            },
            {
              week: 202504,
              mergeRequestCount: 0,
              commitCount: 0,
              commentCount: 7,
            },
            {
              week: 202503,
              mergeRequestCount: 1,
              commitCount: 2,
              commentCount: 0,
            },
            {
              week: 202502,
              mergeRequestCount: 0,
              commitCount: 0,
              commentCount: 0,
            },
          ],
        },
        {
          userId: 22164,
          name: "송용인",
          userName: "thddyddls1",
          avatarUrl:
            "https://secure.gravatar.com/avatar/e7774b0ad0cbd05c15ae5c6bc5a6ddcdcdb8f97976283cd6de24a9c4c52d1faf?s=80&d=identicon",
          totalCommit: 42,
          totalMergeRequest: 20,
          totalComment: 0,
          weeklyInfo: [
            {
              week: 202507,
              mergeRequestCount: 1,
              commitCount: 2,
              commentCount: 0,
            },
            {
              week: 202506,
              mergeRequestCount: 2,
              commitCount: 2,
              commentCount: 0,
            },
            {
              week: 202505,
              mergeRequestCount: 15,
              commitCount: 15,
              commentCount: 0,
            },
            {
              week: 202504,
              mergeRequestCount: 1,
              commitCount: 20,
              commentCount: 0,
            },
            {
              week: 202503,
              mergeRequestCount: 1,
              commitCount: 3,
              commentCount: 0,
            },
            {
              week: 202502,
              mergeRequestCount: 0,
              commitCount: 0,
              commentCount: 0,
            },
          ],
        },
        {
          userId: 22204,
          name: "조창훈",
          userName: "ckdgns6078",
          avatarUrl:
            "https://secure.gravatar.com/avatar/1ee5b9b1573716725c6e5a318973071ee8ca657c2892e497949f9de53f947f0b?s=80&d=identicon",
          totalCommit: 168,
          totalMergeRequest: 50,
          totalComment: 2,
          weeklyInfo: [
            {
              week: 202507,
              mergeRequestCount: 2,
              commitCount: 3,
              commentCount: 0,
            },
            {
              week: 202506,
              mergeRequestCount: 47,
              commitCount: 162,
              commentCount: 1,
            },
            {
              week: 202505,
              mergeRequestCount: 0,
              commitCount: 0,
              commentCount: 1,
            },
            {
              week: 202504,
              mergeRequestCount: 0,
              commitCount: 0,
              commentCount: 0,
            },
            {
              week: 202503,
              mergeRequestCount: 1,
              commitCount: 3,
              commentCount: 0,
            },
            {
              week: 202502,
              mergeRequestCount: 0,
              commitCount: 0,
              commentCount: 0,
            },
          ],
        },
        {
          userId: 22219,
          name: "이해루",
          userName: "gofn080776",
          avatarUrl:
            "https://secure.gravatar.com/avatar/64d76aebe92226f9ea325dc5d35a44327d62594998d76d6905a47b6a0f61ae92?s=80&d=identicon",
          totalCommit: 139,
          totalMergeRequest: 20,
          totalComment: 56,
          weeklyInfo: [
            {
              week: 202507,
              mergeRequestCount: 2,
              commitCount: 17,
              commentCount: 17,
            },
            {
              week: 202506,
              mergeRequestCount: 14,
              commitCount: 103,
              commentCount: 4,
            },
            {
              week: 202505,
              mergeRequestCount: 2,
              commitCount: 10,
              commentCount: 36,
            },
            {
              week: 202504,
              mergeRequestCount: 1,
              commitCount: 19,
              commentCount: 1,
            },
            {
              week: 202503,
              mergeRequestCount: 1,
              commitCount: 3,
              commentCount: 0,
            },
            {
              week: 202502,
              mergeRequestCount: 0,
              commitCount: 0,
              commentCount: 0,
            },
          ],
        },
      ],
    });
  }),
];

export default dashBoardHandler;
