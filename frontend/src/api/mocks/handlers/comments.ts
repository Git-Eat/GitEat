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
  replyList: Reply[];
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
    prId: 32,
    repoId: 888788,
    userId: 1,
    userName: "송용인",
    avatarUrl: null,
    disId: "1",
    content: "이게 어떤 용도죠?",
    commentType: 1,
    createAt: "2025-02-07T12:34:56.789Z",
    position: null,
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
        comment.replyList?.some(
          (reComment) => reComment.reCommentId === replyId
        )
    );

    if (!comment) {
      return HttpResponse.json(
        { message: "답글을 찾을 수 없습니다." },
        { status: 404 }
      );
    }

    comment.replyList = comment.replyList.filter(
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
        replyList: [],
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
          reCommentId: comment.replyList.length + 1,
          userId: 1,
          userName: "테스트 유저",
          avatarUrl: null,
          disId: String(discussionId),
          content,
          replyType,
          imageName: null,
          createAt: new Date().toISOString(),
        };

        comment.replyList.push(newReply);
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
          comment.replyList?.some(
            (reply) => reply.reCommentId === Number(replyId)
          )
      );

      if (!comment) {
        return HttpResponse.json(
          { message: "해당하는 답글을 찾을 수 없습니다." },
          { status: 404 }
        );
      }

      const replyIndex = comment.replyList?.findIndex(
        (reply) => reply.reCommentId === Number(replyId)
      );

      if (replyIndex === undefined || replyIndex === -1) {
        return HttpResponse.json(
          { message: "답글을 찾을 수 없습니다." },
          { status: 404 }
        );
      }

      const updatedReply = {
        ...comment.replyList[replyIndex],
        content,
        replyType,
      };

      comment.replyList[replyIndex] = updatedReply;

      return HttpResponse.json(updatedReply, { status: 200 });
    } catch (error) {
      return HttpResponse.json(
        { message: `잘못된 요청입니다. ${String(error)}` },
        { status: 400 }
      );
    }
  }),
];

export default commentsHandlers;
