import { useMutation, useQueryClient } from "react-query";
import { createComment } from "../comment";

export const useCreateComment = (repoId: number, prId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      content,
      commentType,
    }: {
      content: string;
      commentType: 0 | 1 | 2;
    }) => {
      return createComment(repoId, prId, content, commentType);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["comments", repoId, prId] });
    },
  });
};
