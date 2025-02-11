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
];

export default dashBoardHandler;
