import { useMutation, useQueryClient } from "react-query";
import { createReply } from "../comment";

export const useCreateReply = (repoId: number, prId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      content,
      replyType,
      discussionId,
    }: {
      content: string;
      replyType: 0 | 1 | 2;
      discussionId: string;
    }) => {
      return createReply(repoId, prId, discussionId, content, replyType);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["comments", repoId, prId] });
    },
  });
};
