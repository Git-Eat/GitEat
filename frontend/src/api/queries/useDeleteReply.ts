import { useMutation, useQueryClient } from "react-query";
import { deleteReply } from "../comment";

export const useDeleteReply = (repoId: number, prId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (reCommentId: number) => deleteReply(repoId, prId, reCommentId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["comments", repoId, prId] });
    },
  });
};
