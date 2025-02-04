import { http, HttpResponse } from "msw";

const comments = [
  {
    commentId: 1,
    prId: 1,
    repoId: 1,
    userName: "USER-01",
    avatarUrl: null,
    disId: "1",
    content:
      "# 제목 예시입니다.\n\n## 제목 예시입니다.\n\n### 제목 예시입니다.\n\n#### 제목 예시입니다.\n\n##### 제목 예시입니다.\n\n###### 제목 예시입니다.\n\n본문 예시입니다.",
    commenType: 1,
    imageName: null,
    createAt: "2025-01-27T12:00:00Z",
    replyList: [
      {
        reCommentId: 1,
        userName: "USER-02",
        avatarUrl: null,
        disId: "4",
        content:
          "## 첫 번째 대댓글은 **볼드체**와 *이탤릭체* 그리고 [링크](https://example.com)가 포함되어 있습니다.",
        replyType: 1,
        imageName: null,
        createAt: "2025-02-02T08:05:00Z",
      },
      {
        reCommentId: 2,
        userName: "USER-03",
        avatarUrl: null,
        disId: "4",
        content: "## 두 번째 대댓글입니다.",
        replyType: 2,
        imageName: null,
        createAt: "2025-02-03T06:05:00Z",
      },
    ],
  },
  {
    commentId: 2,
    prId: 1,
    repoId: 1,
    userName: "USER-02",
    avatarUrl: null,
    disId: "2",
    content:
      "## 두 번째 댓글\n\n이 댓글은 **볼드체**와 *이탤릭체* 그리고 [링크](https://example.com)가 포함되어 있습니다.",
    commenType: 2,
    createAt: "2025-02-01T12:05:00Z",
    replyList: [
      {
        reCommentId: 2,
        userName: "USER-04",
        avatarUrl: null,
        disId: "4",
        content: "## 첫 번째 대댓글입니다.",
        replyType: 3,
        imageName: null,
        createAt: "2025-02-03T08:39:00Z",
      },
    ],
  },
  {
    commentId: 3,
    prId: 1,
    repoId: 1,
    userName: "USER-03",
    disId: "3",
    avatarUrl: null,
    content:
      "### 세 번째 댓글\n\n- 리스트 항목 1\n- 리스트 항목 2\n\n코드 예시:\n\n```javascript\nconst foo = 'bar';\nconsole.log(foo);\n```",
    commentType: "review",
    createAt: "2025-02-03T11:46:00Z",
    replyList: [],
  },
];

const commentsHandlers = [
  // 주소 임의 설정(추후 수정 필요)
  http.get("http://backendApi:8080/pr/:repoId/:prId/comment", (req) => {
    const repoId = Number(req.params.repoId);
    const prId = Number(req.params.prId);
    const filteredComments = comments.filter(
      (comment) => comment.repoId === repoId && comment.prId === prId
    );
    return HttpResponse.json(filteredComments, { status: 200 });
  }),

  http.delete(
    "http://backendApi:8080/pr/:repoId/:prId/comment/:commentId",
    (req) => {
      const repoId = Number(req.params.repoId);
      const prId = Number(req.params.prId);
      const commentId = Number(req.params.commentId);
      const commentIndex = comments.findIndex(
        (comment) =>
          comment.repoId === repoId &&
          comment.prId === prId &&
          comment.commentId === commentId
      );
      if (commentIndex !== -1) {
        comments.splice(commentIndex, 1);
        return HttpResponse.json(
          { message: "댓글을 삭제했습니다." },
          { status: 200 }
        );
      } else {
        return HttpResponse.json(
          { message: "댓글을 찾을 수 없습니다." },
          { status: 404 }
        );
      }
    }
  ),
  // http.post("api/pr_id/comments/:comment_id", () => {}),
  // http.update("api/pr_id/comments/:comment_id", () => {}),
];

export default commentsHandlers;
