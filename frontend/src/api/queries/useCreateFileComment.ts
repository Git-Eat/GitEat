import { useMutation } from "react-query";
import { Comment, FileCommentRequest } from "../types/Comment";
import { createFileComment } from "../pullRequest";
import { usePRStore } from "../../store/pullRequestStore";

export const useCreateFileComment = (repoId: number, prId: number) => {
  const { setComments, comments } = usePRStore();
  return useMutation(
    ["addComment", repoId, prId],
    (comment: FileCommentRequest) => createFileComment(repoId, prId, comment),
    {
      onSuccess: (data) => {
        setComments([...comments, data as Comment]);
      },
    }
  );
};
