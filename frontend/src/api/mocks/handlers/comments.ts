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
const UPLOAD_MOCK = {
  markdown:
    "![스크린샷_2025-02-08_23.22.32](/uploads/650b18528a659ce5b887167ada2ec2b9/스크린샷_2025-02-08_23.22.32.png)",
  full_path:
    "/-/project/888788/uploads/650b18528a659ce5b887167ada2ec2b9/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2025-02-08_23.22.32.png",
};

const API_BASE = import.meta.env.VITE_API_BASE;
const commentsHandlers = [
  http.get(API_BASE + `/pr/:repoId/:prId/comment`, (req) => {
    const repoId = Number(req.params.repoId);
    const prId = Number(req.params.prId);
    const filteredComments = comments.filter(
      (comment) => comment.repoId === repoId && comment.prId === prId
    );
    return HttpResponse.json(filteredComments, { status: 200 });
  }),

  http.delete(API_BASE + `/pr/:repoId/:prId/comment/:commentId`, (req) => {
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
  }),
  http.delete(API_BASE + `/pr/:repoId/:prId/reply/:replyId`, (req) => {
    const repoId = Number(req.params.repoId);
    const prId = Number(req.params.prId);
    const replyId = Number(req.params.replyId);
    const comment = comments.find(
      (comment) =>
        comment.repoId === repoId &&
        comment.prId === prId &&
        comment.replyList.some((reComment) => reComment.reCommentId === replyId)
    );
    if (!comment) {
      return HttpResponse.json(
        { message: "답글을 찾을 수 없습니다." },
        { status: 404 }
      );
    }
    comment.replyList = comment.replyList.filter(
      (reComment) => reComment.reCommentId != replyId
    );
    return HttpResponse.json(
      { message: "답글을 삭제했습니다." },
      { status: 200 }
    );
  }),
  // http.post(`${API_BASE}/pr/:repoId/uploads`, async () => {
  //   // 3초(3000ms) 지연
  //   return HttpResponse.json(UPLOAD_MOCK);
  // }),
  // http.post("api/pr_id/comments/:commentId", () => {}),
  // http.update("api/pr_id/comments/:commentId", () => {}),
];

export default commentsHandlers;
