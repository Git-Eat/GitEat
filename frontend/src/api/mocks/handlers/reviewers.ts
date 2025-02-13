import { http, HttpResponse } from "msw";

const reviewers = {
  reviewer: [
    {
      userId: 22147,
      userName: "0903jihyie",
      name: "신지혜",
      avatarUrl:
        "https://secure.gravatar.com/avatar/5a7047c33f01f87edfef9789e87cc5e3604ac367b6589f9e8a43bf7465ab8e24?s=80&d=identicon",
      commentType: 0,
    },
    {
      userId: 22147,
      userName: "0903jihyie",
      name: "신지혜",
      avatarUrl:
        "https://secure.gravatar.com/avatar/5a7047c33f01f87edfef9789e87cc5e3604ac367b6589f9e8a43bf7465ab8e24?s=80&d=identicon",
      commentType: 0,
    },
    {
      userId: 22219,
      userName: "gofn080776",
      name: "이해루",
      avatarUrl:
        "https://secure.gravatar.com/avatar/64d76aebe92226f9ea325dc5d35a44327d62594998d76d6905a47b6a0f61ae92?s=80&d=identicon",
      commentType: 0,
    },
  ],
};

const reviewersHandlers = [
  http.get("*/pr/:repoId/:prId/reviewer", () => {
    return HttpResponse.json(reviewers, { status: 200 });
  }),
];

export default reviewersHandlers;
