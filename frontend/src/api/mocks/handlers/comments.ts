import { http, HttpResponse } from "msw";

const comments = [
  {
    commentId: 1,
    prId: 32,
    repoId: 888788,
    userId: 1,
    userName: "송용인",
    avatarUrl: null,
    disId: "1",
    content: "이게 어떤 용도죠?",
    content: "이게 어떤 용도죠?",
    commentType: 1,
    createAt: null,
    createAt: null,
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
    replyList: [
    replyList: [
      {
        reCommentId: 1,
        userId: 2,
        userName: "조창훈",
        avatarUrl: null,
        disId: "1",
        content: "답장입니다",
        replyType: 0,
        content: "답장입니다",
        replyType: 0,
        imageName: null,
        createAt: null,
        createAt: null,
      },
    ],
  },
  {
    commentId: 2,
    prId: 32,
    repoId: 888788,
    userId: 1,
    userName: "송용인",
    userId: 1,
    userName: "송용인",
    avatarUrl: null,
    disId: "2",
    content: "오 수고하셨습니다 ㅎㅎ",
    content: "오 수고하셨습니다 ㅎㅎ",
    commentType: 1,
    createAt: null,
    createAt: null,
    position: null,
    replyList: [],
  },
];
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
  // http.post("api/pr_id/comments/:commentId", () => {}),
  // http.update("api/pr_id/comments/:commentId", () => {}),
];

export default commentsHandlers;
