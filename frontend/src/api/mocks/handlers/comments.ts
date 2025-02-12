import { http, HttpResponse } from "msw";

interface Comment {
  commentId: number;
  prId: number;
  repoId: number;
  userId: number;
  userName: string;
  avatarUrl: string | null;
  disId: string;
  content: string;
  commentType: 0 | 1 | 2;
  createAt: string | null;
  position: object | null;
  reCommentList: Reply[];
}

interface Reply {
  reCommentId: number;
  userId: number;
  userName: string;
  avatarUrl: string | null;
  disId: string;
  content: string;
  replyType: 0 | 1 | 2;
  imageName: string | null;
  createAt: string | null;
}

interface CommentBody {
  content: string;
  commentType: 0 | 1 | 2;
}

interface ReplyBody {
  content: string;
  replyType: 0 | 1 | 2;
}

const comments: Comment[] = [
  {
    commentId: 1,
    prId: 1,
    repoId: 101,
    userId: 1,
    userName: "송용인",
    avatarUrl: null,
    disId: "1",
    content: "이게 어떤 용도죠?",
    commentType: 1,
    createAt: "2025-02-07T12:34:56.789Z",
    position: null,
    reCommentList: [
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
    prId: 1,
    repoId: 101,
    userId: 1,
    userName: "송용인",
    avatarUrl: null,
    disId: "2",
    content: "오 수고하셨습니다 ㅎㅎ",
    commentType: 1,
    createAt: null,
    position: null,
    reCommentList: [],
  },
  {
    commentId: 3,
    prId: 101,
    repoId: 1,
    userId: 303,
    userName: "commentUser",
    avatarUrl: "https://example.com/avatar1.jpg",
    disId: "dis123",
    content: "example1.txt 파일에서 코드 수정이 필요합니다.",
    commentType: 1,
    createAt: "2025-02-10T15:30:00Z",
    position: {
      baseSha: "commit1_baseSha_dummy",
      startSha: "commit1_startSha_dummy",
      headSha: "commit1_headSha_dummy",
      oldPath: "/src/old/example1.txt",
      newPath: "/src/new/example1.txt",
      positionType: "text",
      newLine: 1,
      oldLine: null,
      newStartLine: 10,
      newEndLine: 20,
      oldStartLine: 9,
      oldEndLine: 19,
      lineRange: {
        start: {
          lineCode: "lineCode_start_dummy",
        },
        end: {
          lineCode: "lineCode_end_dummy",
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
        content: "답장입니다",
        replyType: 0,
        imageName: null,
        createAt: null,
      },
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
    commentId: 4,
    prId: 101,
    repoId: 1,
    userId: 305,
    userName: "anotherCommentUser",
    avatarUrl: "https://example.com/avatar2.jpg",
    disId: "dis456",
    content: "변경된 코드가 성능에 영향을 줄 수 있습니다.",
    commentType: 2,
    createAt: "2025-02-10T17:00:00Z",
    position: null,
    reCommentList: [],
  },
];

const commentsHandlers = [
  http.get("*/pr/:repoId/:prId/comment", () => {
    return HttpResponse.json(comments, { status: 200 });
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
        comment.reCommentList?.some(
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
      (reComment) => reComment.reCommentId !== replyId
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

      const newComment: Comment = {
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
        reCommentList: [],
      };

      comments.push(newComment);
      return HttpResponse.json(newComment, { status: 201 });
    } catch (error) {
      return HttpResponse.json(
        { message: `잘못된 요청입니다. ${String(error)}` },
        { status: 400 }
      );
    }
  }),

  http.post(
    "*/pr/:repoId/:prId/reply/:discussionId",
    async ({ params, request }) => {
      try {
        const { repoId, prId, discussionId } = params;
        const body = (await request.json()) as ReplyBody;
        const { content, replyType } = body;

        if (!content.trim()) {
          return HttpResponse.json(
            { message: "내용을 입력해주세요." },
            { status: 400 }
          );
        }

        const comment = comments.find(
          (req) =>
            req.repoId === Number(repoId) &&
            req.prId === Number(prId) &&
            req.disId === discussionId
        );

        if (!comment) {
          return HttpResponse.json(
            { message: "해당하는 댓글을 찾을 수 없습니다." },
            { status: 404 }
          );
        }

        const newReply: Reply = {
          reCommentId: comment.reCommentList.length + 1,
          userId: 1,
          userName: "테스트 유저",
          avatarUrl: null,
          disId: String(discussionId),
          content,
          replyType,
          imageName: null,
          createAt: new Date().toISOString(),
        };

        comment.reCommentList.push(newReply);
        return HttpResponse.json(newReply, { status: 201 });
      } catch (error) {
        return HttpResponse.json(
          { message: `잘못된 요청입니다. ${String(error)}` },
          { status: 400 }
        );
      }
    }
  ),

  http.put(
    "*/pr/:repoId/:prId/comment/:commentId",
    async ({ params, request }) => {
      try {
        const { repoId, prId, commentId } = params;
        const body = (await request.json()) as CommentBody;
        const { content, commentType } = body;

        const commentIndex = comments.findIndex(
          (comment) =>
            comment.repoId === Number(repoId) &&
            comment.prId === Number(prId) &&
            comment.commentId === Number(commentId)
        );

        if (commentIndex === -1) {
          return HttpResponse.json(
            { message: "댓글을 찾을 수 없습니다." },
            { status: 404 }
          );
        }

        const updatedComment = {
          ...comments[commentIndex],
          content,
          commentType,
        };
        comments[commentIndex] = updatedComment;

        return HttpResponse.json(updatedComment, { status: 200 });
      } catch (error) {
        return HttpResponse.json(
          { message: `잘못된 요청입니다. ${String(error)}` },
          { status: 400 }
        );
      }
    }
  ),

  http.put("*/pr/:repoId/:prId/reply/:replyId", async ({ params, request }) => {
    try {
      const { repoId, prId, replyId } = params;
      const body = (await request.json()) as ReplyBody;
      const { content, replyType } = body;

      const comment = comments.find(
        (comment) =>
          comment.repoId === Number(repoId) &&
          comment.prId === Number(prId) &&
          comment.reCommentList?.some(
            (reply) => reply.reCommentId === Number(replyId)
          )
      );

      if (!comment) {
        return HttpResponse.json(
          { message: "해당하는 답글을 찾을 수 없습니다." },
          { status: 404 }
        );
      }

      const replyIndex = comment.reCommentList?.findIndex(
        (reply) => reply.reCommentId === Number(replyId)
      );

      if (replyIndex === undefined || replyIndex === -1) {
        return HttpResponse.json(
          { message: "답글을 찾을 수 없습니다." },
          { status: 404 }
        );
      }

      const updatedReply = {
        ...comment.reCommentList[replyIndex],
        content,
        replyType,
      };

      comment.reCommentList[replyIndex] = updatedReply;

      return HttpResponse.json(updatedReply, { status: 200 });
    } catch (error) {
      return HttpResponse.json(
        { message: `잘못된 요청입니다. ${String(error)}` },
        { status: 400 }
      );
    }
  }),
  http.post("https://i12b108.p.ssafy.io/api/pr/888788/129/file/comment", () => {
    return HttpResponse.json(200);
  }),
];

export default commentsHandlers;
