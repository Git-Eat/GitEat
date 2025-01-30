import { http, HttpResponse } from "msw";

const comments = [
  {
    pr_id: 1,
    comment_id: 1,
    content: "첫 번째 댓글",
    comment_type: "suggest",
    create_at: "2025-01-30T12:00:00Z",
  },
  {
    pr_id: 1,
    comment_id: 2,
    content: "두 번째 댓글",
    comment_type: "comment",
    create_at: "2025-01-30T12:05:00Z",
  },
  {
    pr_id: 1,
    comment_id: 3,
    content: "세 번째 댓글",
    comment_type: "review",
    create_at: "2025-01-30T12:10:00Z",
  },
];

const commentsHandlers = [
  // 주소 임의 설정(추후 수정 필요)
  http.get("/api/pr_id/comments", () => {
    return HttpResponse.json(comments, { status: 200 });
  }),

  // http.post("api/pr_id/comments/:comment_id", () => {}),
  // http.delete("api/pr_id/comments/:comment_id", () => {}),
  // http.update("api/pr_id/comments/:comment_id", () => {}),
];

export default commentsHandlers;
