import { useMutation, useQueryClient } from "react-query";
import { FileCommentRequest } from "../types/Comment";
import { createFileComment } from "../pullRequest";

export const useCreateFileComment = (repoId: number, prId: number) => {
  const queryClient = useQueryClient();
  return useMutation(
    ["addComment", repoId, prId],
    (comment: FileCommentRequest) => createFileComment(repoId, prId, comment),
    {
      onSuccess: () => {
        queryClient.invalidateQueries({
          queryKey: ["comments", repoId, prId],
        });
      },
    }
  );
};
