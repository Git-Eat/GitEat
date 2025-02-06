import { useMutation, useQueryClient } from "react-query";
import { deleteComment } from "../comment";

export const useDeleteComment = (repoId: number, prId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (commentId: number) => deleteComment(repoId, prId, commentId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["comments", repoId, prId] });
    },
  });
};
