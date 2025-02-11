import { useMutation } from "react-query";
import { deleteReply } from "../comment";
import { usePRStore } from "../../store/pullRequestStore";

export const useDeleteReply = (repoId: number, prId: number) => {
  const { comments, setComments } = usePRStore();

  return useMutation({
    mutationFn: (reCommentId: number) => deleteReply(repoId, prId, reCommentId),
    onSuccess: (_, reCommentId) => {
      setComments(
        comments.map((comment) => ({
          ...comment,
          reCommentList: comment.reCommentList.filter(
            (reply) => reply.reCommentId !== reCommentId
          ),
        }))
      );
    },
  });
};
