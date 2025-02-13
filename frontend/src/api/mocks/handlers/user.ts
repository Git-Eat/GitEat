import { http, HttpResponse } from "msw";
const API_BASE = import.meta.env.VITE_API_BASE;
export const userHandler = [
  http.get(`${API_BASE}/oauth/gitlab/userinfo`, async () => {
    return HttpResponse.json({
      avatar_url:
        "https://secure.gravatar.com/avatar/64d76aebe92226f9ea325dc5d35a44327d62594998d76d6905a47b6a0f61ae92?s=80&d=identicon",
      name: "이해루",
      id: "22219",
      email: "gofn080776@gmail.com",
      username: "gofn080776",
    });
  }),
];
