import { useMutation, useQueryClient } from "react-query";
import { updateComment } from "../comment";

export const useUpdateComment = (repoId: number, prId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      commentId,
      content,
    }: {
      commentId: number;
      content: string;
      commentType: number;
    }) => updateComment(repoId, prId, commentId, content),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["comments", repoId, prId] });
    },
  });
};
