import { useMutation } from "react-query";
import { FileCommentRequest } from "../types/Comment";
import { createFileComment } from "../pullRequest";

export const useCreateFileComment = (repoId: number, prId: number) => {
  return useMutation(
    ["addComment", repoId, prId],
    (comment: FileCommentRequest) => createFileComment(repoId, prId, comment)
  );
};
