import { http, HttpResponse } from "msw";

const comments = [
  {
    pr_id: 1,
    comment_id: 1,
    content:
      "# 제목 예시입니다.\n\n## 제목 예시입니다.\n\n### 제목 예시입니다.\n\n#### 제목 예시입니다.\n\n##### 제목 예시입니다.\n\n###### 제목 예시입니다.\n\n본문 예시입니다.",
    comment_type: "suggest",
    create_at: "2025-01-30T12:00:00Z",
  },
  {
    pr_id: 1,
    comment_id: 2,
    content:
      "## 두 번째 댓글\n\n이 댓글은 **볼드체**와 *이탤릭체* 그리고 [링크](https://example.com)가 포함되어 있습니다.",
    comment_type: "comment",
    create_at: "2025-01-30T12:05:00Z",
  },
  {
    pr_id: 1,
    comment_id: 3,
    content:
      "### 세 번째 댓글\n\n- 리스트 항목 1\n- 리스트 항목 2\n\n코드 예시:\n\n```javascript\nconst foo = 'bar';\nconsole.log(foo);\n```",
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
