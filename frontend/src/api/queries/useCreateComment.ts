import { useMutation, useQueryClient } from "react-query";
import { createComment } from "../comment";

export const useCreateComment = (repoId: number, prId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ content }: { content: string; commentType: number }) => {
      return createComment(repoId, prId, content);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["comments", repoId, prId] });
    },
  });
};
