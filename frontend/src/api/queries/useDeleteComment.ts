import { useMutation } from "react-query";
import { deleteComment } from "../comment";
import { usePRStore } from "../../store/pullRequestStore";

export const useDeleteComment = (repoId: number, prId: number) => {
  const { comments, setComments } = usePRStore();

  return useMutation({
    mutationFn: (commentId: number) => deleteComment(repoId, prId, commentId),
    onSuccess: (_, commentId) => {
      setComments(
        comments.filter((comment) => comment.commentId !== commentId)
      );
    },
  });
};
