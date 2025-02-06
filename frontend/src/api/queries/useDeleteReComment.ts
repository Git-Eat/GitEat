import { useMutation, useQueryClient } from "react-query";
import { deleteReComment } from "../comment";

export const useDeleteReComment = (repoId: number, prId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (reCommentId: number) =>
      deleteReComment(repoId, prId, reCommentId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["comments", repoId, prId] });
    },
  });
};
