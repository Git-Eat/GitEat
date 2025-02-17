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
  fileId: string | null;
  reCommentList: Reply[];
}

interface Reply {
  reCommentId: number;
  userId: number;
  userName: string;
  avatarUrl: string | null;
  disId: string;
  content: string;
  reCommentType: 0 | 1 | 2;
  imageName: string | null;
  createAt: string | null;
}

interface CommentBody {
  content: string;
  commentType: 0 | 1 | 2;
}

interface ReplyBody {
  content: string;
  reCommentType: 0 | 1 | 2;
}

const comments: Comment[] = [
  {
    commentId: 1879447,
    prId: 32,
    repoId: 888788,
    userId: 21879,
    fileId: "a109f76d4b7d7f71f4283fce95cb02f8a5ee4b07",
    userName: "최이화",
    avatarUrl:
      "https://secure.gravatar.com/avatar/c1cfcd69bd0f4f338373504e930553ebbef699aa0fb6c3c7ddf2cec52d94e76c?s=80&d=identicon",
    disId: "f83b0660317d1f6f4131c7ecc81a73da8ab25416",
    content:
      "이 부분이 파일명만 보여주는 건지 파일 경로를 보여주는 건지 알고 싶습니다.\n제 생각에는 현재처럼 파일명을 보여주고, 아래에 추가로 파일 경로를 기입하는 것도 괜찮을 것 같습니다.",
    commentType: 0,
    createAt: "2025-02-02T16:25:26.918+09:00",
    position: {
      baseSha: "5b7a6146752b83f400e07854dfe27bf7000cf058",
      startSha: "5b7a6146752b83f400e07854dfe27bf7000cf058",
      headSha: "74523366418dcf66994fb3c319344be2bc2c0533",
      oldPath: "frontend/src/components/pullRequest/fileChanges/index.tsx",
      newPath: "frontend/src/components/pullRequest/fileChanges/index.tsx",
      positionType: null,
      newLine: 75,
      oldLine: 0,
      newStartLine: 74,
      newEndLine: 74,
      oldStartLine: 0,
      oldEndLine: 0,
      lineRange: null,
    },
    reCommentList: [],
  },
  {
    commentId: 1879448,
    prId: 32,
    repoId: 888788,
    userId: 21879,
    fileId: null,
    userName: "최이화",
    avatarUrl:
      "https://secure.gravatar.com/avatar/c1cfcd69bd0f4f338373504e930553ebbef699aa0fb6c3c7ddf2cec52d94e76c?s=80&d=identicon",
    disId: "99c367b889f342264456007f4ae86ad3180aa356",
    content: "확인했습니다!",
    commentType: 0,
    createAt: "2025-02-02T16:25:27.216+09:00",
    position: null,
    reCommentList: [],
  },
  {
    commentId: 1879970,
    prId: 32,
    repoId: 888788,
    userId: 22204,
    fileId: null,
    userName: "조창훈",
    avatarUrl:
      "https://secure.gravatar.com/avatar/1ee5b9b1573716725c6e5a318973071ee8ca657c2892e497949f9de53f947f0b?s=80&d=identicon",
    disId: "65210a4205de56d91824672c1fe9fe8f8f59b116",
    content: "고생하셨습니다 :smile:",
    commentType: 0,
    createAt: "2025-02-02T21:10:39.268+09:00",
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
        fileId: null,
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
        const { content, reCommentType } = body;

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
          reCommentType,
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
      const { content, reCommentType } = body;

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
        reCommentType,
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
