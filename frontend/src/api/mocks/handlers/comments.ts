import { http, HttpResponse } from "msw";

const comments = [
  {
    prId: 1,
    commentId: 1,
    userId: "USER-01",
    content:
      "# 제목 예시입니다.\n\n## 제목 예시입니다.\n\n### 제목 예시입니다.\n\n#### 제목 예시입니다.\n\n##### 제목 예시입니다.\n\n###### 제목 예시입니다.\n\n본문 예시입니다.",
    commenType: "suggest",
    createAt: "2025-01-27T12:00:00Z",
  },
  {
    prId: 1,
    commentId: 2,
    userId: "USER-02",
    content:
      "## 두 번째 댓글\n\n이 댓글은 **볼드체**와 *이탤릭체* 그리고 [링크](https://example.com)가 포함되어 있습니다.",
    commenType: "comment",
    createAt: "2025-02-01T12:05:00Z",
  },
  {
    prId: 1,
    commentId: 3,
    userId: "USER-03",
    content:
      "### 세 번째 댓글\n\n- 리스트 항목 1\n- 리스트 항목 2\n\n코드 예시:\n\n```javascript\nconst foo = 'bar';\nconsole.log(foo);\n```",
    commentType: "review",
    createAt: "2025-02-03T11:46:00Z",
  },
];

const commentsHandlers = [
  // 주소 임의 설정(추후 수정 필요)
  http.get("http://localhost:3000/undefined/pr/repoId/prId/comment", () => {
    return HttpResponse.json(comments, { status: 200 });
  }),

  // http.post("api/pr_id/comments/:comment_id", () => {}),
  // http.delete("api/pr_id/comments/:comment_id", () => {}),
  // http.update("api/pr_id/comments/:comment_id", () => {}),
];

export default commentsHandlers;
