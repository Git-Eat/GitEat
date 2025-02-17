import { http, HttpResponse } from "msw";
const API_BASE = import.meta.env.VITE_API_BASE;
const fileCommentHandler = [
  http.put(`${API_BASE}/repo`, async () => {
    // 3초(3000ms) 지연
    await new Promise((resolve) => setTimeout(resolve, 3000));
    return HttpResponse.json({ status: 200 });
  }),

  // 3초 후에 실패하는 핸들러
  http.post(`${API_BASE}/pr/888788/13/file/comment`, async () => {
    // 3초(3000ms) 지연
    await new Promise((resolve) => setTimeout(resolve, 3000));
    return HttpResponse.json({ status: 200 });
  }),
  http.delete(`${API_BASE}/repo/1`, async () => {
    // 3초(3000ms) 지연
    await new Promise((resolve) => setTimeout(resolve, 3000));
    return HttpResponse.json({ status: 200 });
  }),
];

export default fileCommentHandler;
