import { http, HttpResponse } from "msw";
import { ReComments } from "../../../components/pullRequest/conversation/reComments";

const comments = [
  {
    commentId: 1,
    prId: 32,
    repoId: 888788,
    userId: 1,
    userName: "송용인",
    avatarUrl: null,
    disId: "1",
    content:
      "# 제목 예시입니다.\n\n## 제목 예시입니다.\n\n### 제목 예시입니다.\n\n#### 제목 예시입니다.\n\n##### 제목 예시입니다.\n\n###### 제목 예시입니다.\n\n본문 예시입니다.",
    commentType: 1,
    createAt: "2025-01-27T12:00:00Z",
    position: {
      baseSha: "5b7a6146752b83f400e07854dfe27bf7000cf058",
      startSha: "5b7a6146752b83f400e07854dfe27bf7000cf058",
      headSha: "74523366418dcf66994fb3c319344be2bc2c0533",
      oldPath:
        "frontend/src/components/pullRequest/conversation/comments/index.tsx",
      newPath:
        "frontend/src/components/pullRequest/conversation/comments/index.tsx",
      positionType: "text",
      newLine: 74,
      oldLine: null,
      newStartLine: 75,
      newEndLine: 75,
      oldStartLine: null,
      oldEndLine: null,
      lineRange: {
        start: {
          lineCode: "809f9e3430dfcad1380bf902c9afd29209973874_0_75",
        },
        end: {
          lineCode: "809f9e3430dfcad1380bf902c9afd29209973874_0_75",
        },
      },
    },
    reCommentList: [
      {
        reCommentId: 1,
        userId: 2,
        userName: "조창훈",
        avatarUrl: null,
        disId: "1",
        content:
          "## 첫 번째 대댓글은 **볼드체**와 *이탤릭체* 그리고 [링크](https://example.com)가 포함되어 있습니다.",
        reCommentType: 0,
        imageName: null,
        createAt: "2025-02-01T18:00:00Z",
      },
      {
        reCommentId: 2,
        userId: 3,
        userName: "신지혜",
        avatarUrl: null,
        disId: "1",
        content: "## 두 번째 대댓글입니다.",
        reCommentType: 0,
        imageName: null,
        createAt: "2025-02-03T07:00:00Z",
      },
    ],
  },
  {
    commentId: 2,
    prId: 32,
    repoId: 888788,
    userId: 4,
    userName: "최이화",
    avatarUrl: null,
    disId: "2",
    content:
      "## 두 번째 댓글\n\n이 댓글은 **볼드체**와 *이탤릭체* 그리고 [링크](https://example.com)가 포함되어 있습니다.",
    commentType: 1,
    createAt: "2025-01-28T12:00:00Z",
    position: null,
    reCommentList: [
      {
        reCommentId: 1,
        userId: 5,
        userName: "이다영",
        avatarUrl: null,
        disId: "1",
        content: "## 첫 번째 대댓글입니다.",
        reCommentType: 2,
        imageName: null,
        createAt: "2025-01-30T05:02:10Z",
      },
    ],
  },
  {
    commentId: 3,
    prId: 32,
    repoId: 888788,
    userId: 6,
    userName: "이해루",
    avatarUrl: null,
    disId: "2",
    content:
      "### 세 번째 댓글\n\n- 리스트 항목 1\n- 리스트 항목 2\n\n코드 예시:\n\n```javascript\nconst foo = 'bar';\nconsole.log(foo);\n```",
    commentType: 2,
    createAt: "2025-02-03T01:00:00Z",
    position: null,
    reCommentList: [],
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
  http.delete(
    "http://backendApi:8080/pr/:repoId/:prId/reply/:replyId",
    (req) => {
      const repoId = Number(req.params.repoId);
      const prId = Number(req.params.prId);
      const replyId = Number(req.params.replyId);
      const comment = comments.find(
        (comment) =>
          comment.repoId === repoId &&
          comment.prId === prId &&
          comment.reCommentList.some(
            (reComment) => reComment.reCommentId === replyId
          )
      );
      if (!comment) {
        return HttpResponse.json(
          { message: "답글을 찾을 수 없습니다." },
          { status: 404 }
        );
      }
      comment.reCommentList = comment.reCommentList.filter(
        (reComment) => reComment.reCommentId != replyId
      );
      return HttpResponse.json(
        { message: "답글을 삭제했습니다." },
        { status: 200 }
      );
    }
  ),
  // http.post("api/pr_id/comments/:commentId", () => {}),
  // http.update("api/pr_id/comments/:commentId", () => {}),
];

export default commentsHandlers;
