import { http, HttpResponse } from "msw";

interface CommentBody {
  content: string;
  commentType: 0 | 1 | 2;
}

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
    commentType: 1,
    createAt: "2025-02-07T12:34:56.789Z",
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
      {
        reCommentId: 1,
        userId: 2,
        userName: "조창훈",
        avatarUrl: null,
        disId: "1",
        content: "답장입니다",
        replyType: 0,
        imageName: null,
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
    avatarUrl: null,
    disId: "2",
    content: "오 수고하셨습니다 ㅎㅎ",
    commentType: 1,
    createAt: null,
    position: null,
    replyList: [],
  },
];

const commentsHandlers = [
  http.get("*/pr/:repoId/:prId/comment", (req) => {
    const repoId = Number(req.params.repoId);
    const prId = Number(req.params.prId);
    const filteredComments = comments.filter(
      (comment) => comment.repoId === repoId && comment.prId === prId
    );
    return HttpResponse.json(filteredComments, { status: 200 });
  }),

  http.delete("*/pr/:repoId/:prId/comment/:commentId", (req) => {
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
  http.delete("*/pr/:repoId/:prId/reply/:replyId", (req) => {
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
  http.post("*/pr/:repoId/:prId/comment", async ({ params, request }) => {
    try {
      const { repoId, prId } = params;
      const body = (await request.json()) as CommentBody;
      const { content, commentType } = body;

      if (!content.trim()) {
        return HttpResponse.json(
          { message: "내용을 입력해주세요." },
          { status: 400 }
        );
      }

      const newComment = {
        commentId: comments.length + 1,
        prId: Number(prId),
        repoId: Number(repoId),
        userId: 1,
        userName: "테스트 유저",
        avatarUrl: null,
        disId: `${comments.length + 1}`,
        content,
        commentType,
        createAt: null,
        position: null,
        replyList: [],
      };

      comments.push(newComment);

      return HttpResponse.json(newComment, { status: 201 });
    } catch (error) {
      return HttpResponse.json(
        { message: "잘못된 요청입니다." + error },
        { status: 400 }
      );
    }
  }),

  // http.update("api/pr_id/comments/:commentId", () => {}),
];

export default commentsHandlers;
