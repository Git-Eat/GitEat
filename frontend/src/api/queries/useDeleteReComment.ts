import { useMutation, useQueryClient } from "react-query";
import { deleteReComment } from "../comment";

export const useDeleteReComment = (
  repoId: number,
  prId: number,
  commentId: number
) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (reCommentId: number) =>
      deleteReComment(repoId, prId, commentId, reCommentId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["comments", repoId, prId] });
    },
  });
};
