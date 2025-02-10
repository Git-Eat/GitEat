import { useMutation, useQueryClient } from "react-query";
import { createReply } from "../comment";

export const useCreateReply = (repoId: number, prId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      content,
      discussionId,
    }: {
      content: string;
      replyType: number;
      discussionId: string;
    }) => {
      return createReply(repoId, prId, discussionId, content);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["comments", repoId, prId] });
    },
  });
};
