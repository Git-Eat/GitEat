import { useMutation, useQueryClient } from "react-query";
import { updateReply } from "../comment";

export const useUpdateReply = (repoId: number, prId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      reCommentId,
      content,
    }: {
      reCommentId: number;
      content: string;
      replyType: number;
    }) => updateReply(repoId, prId, reCommentId, content),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["comments", repoId, prId] });
    },
  });
};
