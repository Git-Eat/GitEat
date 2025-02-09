import { useMutation, useQueryClient } from "react-query";
import { updateComment } from "../comment";

export const useUpdateComment = (repoId: number, prId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      commentId,
      content,
      commentType,
    }: {
      commentId: number;
      content: string;
      commentType: 0 | 1 | 2;
    }) => updateComment(repoId, prId, commentId, commentType, content),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["comments", repoId, prId] });
    },
  });
};
