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
    commentId: 1874761,
    prId: 17,
    repoId: 888788,
    userId: 22219,
    userName: "이해루",
    avatarUrl:
      "https://secure.gravatar.com/avatar/64d76aebe92226f9ea325dc5d35a44327d62594998d76d6905a47b6a0f61ae92?s=80&d=identicon",
    disId: "a0124e9547dcd285a942adf3a8986d1006e6279a",
    content: "사용하지 않는 코드라면 지워도 될 것 같습니다. 수정하지마",
    commentType: 0,
    createAt: "2025-02-10T21:17:27.361+09:00",
    position: {
      baseSha: "46edebc6b129916f4e0f2b485add074ccb8f385a",
      startSha: "46edebc6b129916f4e0f2b485add074ccb8f385a",
      headSha: "3d40bc144d918d1eb890c7a373c6072cb812cdd4",
      oldPath:
        "backend/rest/src/main/java/com/giteat/pr/entity/RepositoryEntity.java",
      newPath:
        "backend/rest/src/main/java/com/giteat/pr/entity/RepositoryEntity.java",
      positionType: null,
      newLine: 39,
      oldLine: 0,
      newStartLine: 39,
      newEndLine: 39,
      oldStartLine: 0,
      oldEndLine: 0,
      lineRange: null,
    },
    reCommentList: [],
  },
  {
    commentId: 1874763,
    prId: 17,
    repoId: 888788,
    userId: 22219,
    userName: "이해루",
    avatarUrl:
      "https://secure.gravatar.com/avatar/64d76aebe92226f9ea325dc5d35a44327d62594998d76d6905a47b6a0f61ae92?s=80&d=identicon",
    disId: "6b9fcf9fcfaf00d67cb10020dbd1dbe4ddea4330",
    content:
      "수고하셨습니다. 문득 든 생각인데 pr이라는 명칭에 대해서는 어떻게 생각하시나요? 그렇게 명시적인 이름은 아닌거 같아서 이후 여유가 된다면 pullrequest로 수정하는 것도 나쁘지 않겠다는 생각입니다. 코멘트 남긴 부분만 조금 수정하시면 좋을 것 같습니다!",
    commentType: 0,
    createAt: "2025-01-31T09:50:55.882+09:00",
    position: null,
    reCommentList: [
      {
        reCommentId: 1874817,
        userId: 22147,
        userName: "신지혜",
        avatarUrl:
          "https://secure.gravatar.com/avatar/5a7047c33f01f87edfef9789e87cc5e3604ac367b6589f9e8a43bf7465ab8e24?s=80&d=identicon",
        disId: "6b9fcf9fcfaf00d67cb10020dbd1dbe4ddea4330",
        content:
          "네 ~~ 깃랩에서 Mergerequest라는 명칭으로 쓰고 있어서 이걸로 바꾸려고 합니다 !",
        reCommentType: 0,
        imageName: null,
        createAt: "2025-01-31T09:50:55.829+09:00",
      },
    ],
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
