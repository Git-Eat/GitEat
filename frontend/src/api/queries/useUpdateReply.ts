import { useMutation } from "react-query";
import { updateReply } from "../comment";
import { usePRStore } from "../../store/pullRequestStore";

export const useUpdateReply = (repoId: number, prId: number) => {
  const { comments, setComments } = usePRStore();

  return useMutation({
    mutationFn: ({
      reCommentId,
      content,
    }: {
      reCommentId: number;
      content: string;
      replyType: number;
    }) => updateReply(repoId, prId, reCommentId, content),
    onSuccess: (updateReply) => {
      setComments(
        comments.map((comment) => ({
          ...comment,
          reCommentList: comment.reCommentList.map((reply) =>
            reply.reCommentId === updateReply.reCommentId
              ? { ...reply, content: updateReply.content }
              : reply
          ),
        }))
      );
    },
  });
};
