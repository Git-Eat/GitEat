import { useMutation, useQueryClient } from "react-query";
import { updateReply } from "../comment";

export const useUpdateReply = (repoId: number, prId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      reCommentId,
      content,
      replyType,
    }: {
      reCommentId: number;
      content: string;
      replyType: 0 | 1 | 2;
    }) => updateReply(repoId, prId, reCommentId, replyType, content),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["comments", repoId, prId] });
    },
  });
};
